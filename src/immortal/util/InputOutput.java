package immortal.util;

import immortal.Exceptions.InvalidInputException;

import javax.swing.*;

public class InputOutput {
    public static <T extends JTextField> void verifyNotNull(T ...fields) throws InvalidInputException {
        for(T field : fields) {
            if(field.getText().length() < 1) {
                throw new InvalidInputException("Invalid Entry");
            }
        }
    }
}
