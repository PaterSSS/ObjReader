package org.main.reader.exeptions;

import org.main.model.Group;

public class GroupException extends ObjReaderException {
    public GroupException(int lineIndex) {
        super("Undefined group name", lineIndex);
    }
}
