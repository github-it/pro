public class Validator {
    private static Validator validator;

    public void validateNumber(String number) throws ValidationException {
        try {
			if (number != null){
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("numberError", "Invalid number");
        }
		}
    }
	
	 public void validateLoginEmail(String loginEmail) throws ValidationException{
        
        if (loginEmail == null || EMPTY_STRING.equals(loginEmail) || loginEmail.length() > STRING_MAX_LENGTH){
            throw new ValidationException("loginEmailError", "Invalid loginEmail");
        }

        Pattern pattern = Pattern.compile(loginEmail_REGEX);
        boolean match = pattern.matcher(loginEmail).matches();

        if (!match){
            throw new ValidationException("loginEmailError", "Invalid loginEmail");
        }
    }

    public void validatePassword(String password) throws ValidationException {
        if(password == null || EMPTY_STRING.equals(password) || password.length() > STRING_MAX_LENGTH){
            throw new ValidationException("userPasswordError", "Invalid user password");
        }
    }

    public void validateTextInput(String textInput) throws ValidationException{
        if (textInput == null || EMPTY_STRING.equals(textInput) || textInput.length() > STRING_MAX_LENGTH) {
            throw new ValidationException("Error text input", "Invalid text input");
        }
    }

    public void validatePhoneNumber(String phoneNumer) throws ValidationException{
        try {
			if (phoneNumer != null){
            Long.parseLong(phone);
        } catch (NumberFormatException e) {
            throw new ValidationException("userPhoneError", "Invalid user phone");
        }
		}
    }
}
