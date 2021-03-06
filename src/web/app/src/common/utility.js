export const deepCopy = (obj) => {
  return JSON.parse(JSON.stringify(obj));
};

export const filterPredefinedMap = (mappedIdArray, originalArray) => {
  let result = [];
  for (let i = 0; i < mappedIdArray.length; i++) {
    if (mappedIdArray[i] < originalArray.length) {
      result.push(mappedIdArray[i]);
    }
  }
  return result;
};

/*
  takes in a list of lists and places the item into one of the lists.
  each list should ideally have 1 item.
  tries to place the item up in the list, any excess will be in the last list.
  this mutates xss.
 */
export const distribute = (xss, item) => {
    for (let n = 0; n < xss.length; n++) {
      if (xss[n].length === 0) { // if the list is empty
        xss[n].push(item);
        return;
      }
      if (n === xss.length - 1) { // if the list is the last
        xss[n].push(item);
        return;
      }
    }
};

/*
  takes in a list of lists (call is xss), the column index and the list index.
  removes xss[colIdx][lstIdx].
  this method uses map and filter and hence it does not mutate the original xss.
 */
export const removeItem = (xss, colIdx, lstIdx) => {
  return xss.map((xs, idx) => idx !== colIdx ? xs : xs.filter((x, idx) => idx !== lstIdx));
};

/*
  from a list of names of functions (fnames), return a list of functions corresponding to the original list,
  based on the options available and the meta data of the field.
  this method does not mutate fnames, fieldMetaData, options
 */
export const getTransformations = (fnames, fieldMetaData, options) => {
  return fieldMetaData.map((meta, index) => {
    return options[meta.type].filter(obj => obj.name === fnames[index]).pop();
  });
};

/*
  takes in a list of headers and the Data, creates a list of objects with a header as the field and some data in Data
  as the value.
  this function return exactly the format of data that the functions later on uses.
 */
export const formDataWithHeaders = (headers, data) => {
  let transformedData = [];
  data.forEach(row => {
    let obj = {};
    headers.forEach((header, index) => {
      obj[header] = row[index];
    });
    transformedData.push(obj);
  });
  return transformedData;
};
