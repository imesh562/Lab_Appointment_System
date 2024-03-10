package com.imesh.lab.services;

import com.imesh.lab.dao.RegistrationDao;
import com.imesh.lab.dao.RegistrationDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.UserModel;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationService {
    private static RegistrationService registrationService;

    public static synchronized RegistrationService getService() {
        if (registrationService == null) {
            registrationService = new RegistrationService();
        }
        return registrationService;
    }

    private RegistrationDao getRegistrationDao() {
        return new RegistrationDaoImpl();
    }

    public CommonMessageModel registerCustomer(UserModel userModel) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(regex);
        Matcher emailMatcher = emailPattern.matcher(userModel.getEMail());
        Pattern phonePattern = Pattern.compile("[^\\d+]");
        Matcher phoneMatcher = phonePattern.matcher(userModel.getPhoneNumber());
        if(userModel.getFirstName().length() > 30 || userModel.getFirstName().isEmpty()){
            return new CommonMessageModel("Invalid First Name", false, null);
        } else if (userModel.getLastName().length() > 50 || userModel.getLastName().isEmpty()) {
            return new CommonMessageModel("Invalid Last Name", false, null);
        } else if(!emailMatcher.matches()){
            return new CommonMessageModel("Invalid E-mail", false, null);
        } else if(userModel.getPhoneNumber().length() > 15 || userModel.getPhoneNumber().length() < 6 || phoneMatcher.find()){
            return new CommonMessageModel("Invalid Phone Number", false, null);
        } else if (!(userModel.getPassword().length() >= 6 && userModel.getPassword().length() <= 24)){
            return new CommonMessageModel("Password length must be between 6 and 24 characters.", false, null);
        } else if(!userModel.getPassword().matches(".*[A-Z].*") || !userModel.getPassword().matches(".*\\d.*")){
            return new CommonMessageModel("Password does not meet the criteria: must contain at least one uppercase letter and one number.", false, null);
        }else if(!userModel.getPassword().equals(userModel.getConfirmPassword())){
            return new CommonMessageModel("Passwords does not match", false, null);
        }else if(getRegistrationDao().checkIfUserExists(userModel.getEMail(), userModel.getPhoneNumber())){
            return new CommonMessageModel("User already with the same Email or Phone Number", false, null);
        } else {
            boolean isSuccess = getRegistrationDao().registerCustomer(userModel);
            if(isSuccess){
                return new CommonMessageModel("We have sent you an E-mail. Please check your email for the login information.", true, null);
            } else {
                return new CommonMessageModel("Something went wrong.", false, null);
            }
        }
    }

    public int generateUserCode() throws ClassNotFoundException, SQLException {
        return getRegistrationDao().generateUserCode();
    }
}
