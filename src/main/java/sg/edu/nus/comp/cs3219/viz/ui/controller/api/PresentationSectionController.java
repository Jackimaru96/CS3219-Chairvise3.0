package sg.edu.nus.comp.cs3219.viz.ui.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.comp.cs3219.viz.common.datatransfer.AccessLevel;
import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationNotFoundException;
import sg.edu.nus.comp.cs3219.viz.common.exception.PresentationSectionNotFoundException;
import sg.edu.nus.comp.cs3219.viz.logic.GateKeeper;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationLogic;
import sg.edu.nus.comp.cs3219.viz.logic.PresentationSectionLogic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PresentationSectionController extends BaseRestController {

    private final PresentationLogic presentationLogic;
    private final PresentationSectionLogic presentationSectionLogic;

    private final GateKeeper gateKeeper;

    public PresentationSectionController(PresentationLogic presentationLogic,
                                         PresentationSectionLogic presentationSectionLogic,
                                         GateKeeper gateKeeper) {
        this.presentationLogic = presentationLogic;
        this.presentationSectionLogic = presentationSectionLogic;
        this.gateKeeper = gateKeeper;
    }

    @GetMapping("/presentations/{presentationId}/sections")
    public List<PresentationSection> all(@PathVariable Long presentationId,
                                         @CookieValue(value = "userEmail") String email,
                                         @CookieValue(value = "userPassword") String password) {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_READ, email, password);

        return presentationSectionLogic.findAllByPresentation(presentation);
    }

    @PostMapping("/presentations/{presentationId}/sections")
    public ResponseEntity<?> newPresentationSection(@PathVariable Long presentationId, @RequestBody PresentationSection presentationSection,
                                                    @CookieValue(value = "userEmail") String email,
                                                    @CookieValue(value = "userPassword") String password) throws URISyntaxException {
        Presentation presentation = presentationLogic.findById(presentationId)
                .orElseThrow(() -> new PresentationNotFoundException(presentationId));
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        PresentationSection newPresentationSection = presentationSectionLogic.saveForPresentation(presentation, presentationSection);

        return ResponseEntity
                .created(new URI("/presentations/" + presentationId + "/section/" + newPresentationSection.getId()))
                .body(newPresentationSection);
    }

    @PutMapping("/presentations/{presentationId}/sections/{sectionId}")
    public ResponseEntity<?> updatePresentationSection(@PathVariable Long presentationId, @PathVariable Long sectionId,
                                                       @RequestBody PresentationSection newPresentationSection,
                                                       @CookieValue(value = "userEmail") String email,
                                                       @CookieValue(value = "userPassword") String password) throws URISyntaxException {
        PresentationSection oldPresentationSection = presentationSectionLogic.findById(sectionId)
                .orElseThrow(() -> new PresentationSectionNotFoundException(presentationId, sectionId));

        Presentation presentation = oldPresentationSection.getPresentation();
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        PresentationSection updatedPresentationSection =
                presentationSectionLogic.updatePresentation(oldPresentationSection, newPresentationSection);

        return ResponseEntity
                .created(new URI("/presentations/" + presentationId + "/section/" + updatedPresentationSection.getId()))
                .body(updatedPresentationSection);
    }

    @DeleteMapping("/presentations/{presentationId}/sections/{sectionId}")
    public ResponseEntity<?> deletePresentationSection(@PathVariable Long presentationId, @PathVariable Long sectionId,
                                                       @CookieValue(value = "userEmail") String email,
                                                       @CookieValue(value = "userPassword") String password) {
        PresentationSection presentationSection = presentationSectionLogic.findById(sectionId)
                .orElseThrow(() -> new PresentationSectionNotFoundException(presentationId, sectionId));

        Presentation presentation = presentationSection.getPresentation();
        gateKeeper.verifyAccessForPresentation(presentation, AccessLevel.CAN_WRITE, email, password);

        presentationSectionLogic.deleteById(sectionId);

        return ResponseEntity.noContent().build();
    }

}
