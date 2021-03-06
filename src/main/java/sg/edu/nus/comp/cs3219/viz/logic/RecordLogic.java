package sg.edu.nus.comp.cs3219.viz.logic;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.FileInfo;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.*;
import sg.edu.nus.comp.cs3219.viz.storage.repository.AuthorRecordRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.ReviewRecordRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.SubmissionAuthorRecordRepository;
import sg.edu.nus.comp.cs3219.viz.storage.repository.SubmissionRecordRepository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static sg.edu.nus.comp.cs3219.viz.common.util.Const.*;

@Component
public class RecordLogic {
    private AuthorRecordRepository authorRecordRepository;

    private SubmissionRecordRepository submissionRecordRepository;

    private SubmissionAuthorRecordRepository submissionAuthorRecordRepository;

    private ReviewRecordRepository reviewRecordRepository;

    public RecordLogic(AuthorRecordRepository authorRecordRepository,
                       SubmissionRecordRepository submissionRecordRepository,
                       SubmissionAuthorRecordRepository submissionAuthorRecordRepository,
                       ReviewRecordRepository reviewRecordRepository) {
        this.authorRecordRepository = authorRecordRepository;
        this.submissionRecordRepository = submissionRecordRepository;
        this.submissionAuthorRecordRepository = submissionAuthorRecordRepository;
        this.reviewRecordRepository = reviewRecordRepository;
    }

    @Transactional
    public void removeAndPersistAuthorRecordForUserId(FileRecord fileRecord, List<AuthorRecord> authorRecordList) {
        //authorRecordRepository.deleteAllByDataSetEquals(dataSet);
        authorRecordRepository.saveAll(authorRecordList.stream().peek(r -> {
            // should not set ID when creating records
            r.setId(null);
            // should set dataSet
            r.setFileRecord(fileRecord);
            // the other field can be arbitrary
        }).collect(Collectors.toList()));
    }

    @Transactional
    public void removeAndPersistReviewRecordForUserId(FileRecord fileRecord, List<ReviewRecord> reviewRecordList) {
        //reviewRecordRepository.deleteAllByDataSetEquals(dataSet);
        reviewRecordRepository.saveAll(reviewRecordList.stream().peek(r -> {
            // should not set ID when creating records
            r.setId(null);
            // should set dataSet
            r.setFileRecord(fileRecord);
            // the other field can be arbitrary
        }).collect(Collectors.toList()));
    }

    @Transactional
    public void removeRecordForUserIdFileId(long userId, FileInfo fileInfo) {
        FileId fileId = new FileId();
        fileId.setUserId(userId);
        fileId.setFileNumber(fileInfo.getFileNumber());

        switch (fileInfo.getFileType()) {
            case FILE_TYPE_AUTHOR:
                removeAuthorRecordForFileId(fileId);
                break;

            case FILE_TYPE_SUBMISSION:
                removeSubmissionRecordForFileId(fileId);
                break;

            case FILE_TYPE_REVIEW:
                removeReviewRecordForFileId(fileId);
                break;

            default:
        }
    }

    @Transactional
    public void removeAuthorRecordForFileId(FileId fileId) {
        authorRecordRepository.deleteAllByFileRecordFileIdEquals(fileId);
    }

    @Transactional
    public void removeSubmissionRecordForFileId(FileId fileId) {
        submissionRecordRepository.deleteAllByFileRecordFileIdEquals(fileId);
    }

    @Transactional
    public void removeReviewRecordForFileId(FileId fileId) {
        reviewRecordRepository.deleteAllByFileRecordFileIdEquals(fileId);
    }

    @Transactional
    public void removeAndPersistSubmissionRecordForUserId(FileRecord fileRecord, List<SubmissionRecord> submissionRecordList) {
        //submissionRecordRepository.deleteAllByDataSetEquals(dataSet);
        //submissionAuthorRecordRepository.deleteAllByDataSetEquals(dataSet);
        submissionRecordRepository.saveAll(submissionRecordList.stream().peek(s -> {
            // should not set ID when creating records
            s.setId(null);
            // should set dataSet
            s.setFileRecord(fileRecord);
            // create many to many relationship for authors
            List<SubmissionAuthorRecord> submissionAuthorRecords = s.getAuthors().stream()
                    .map(authorName -> {
                        SubmissionAuthorRecord existing = submissionAuthorRecordRepository.findFirstByNameEqualsAndFileRecordEquals(authorName, fileRecord);
                        if (existing == null) {
                            existing = new SubmissionAuthorRecord();
                            existing.setFileRecord(fileRecord);
                            existing.setName(authorName);
                            existing = submissionAuthorRecordRepository.save(existing);
                        }
                        return existing;
                    })
                    .collect(Collectors.toList());
            s.setAuthorSet(submissionAuthorRecords);
            // the other field can be arbitrary
        }).collect(Collectors.toList()));
    }
}
