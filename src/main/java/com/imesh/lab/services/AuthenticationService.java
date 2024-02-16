package com.imesh.lab.services;

import com.imesh.lab.dao.AuthenticationDao;
import com.imesh.lab.dao.AuthenticationDaoImpl;
import com.imesh.lab.models.CommonMessageModel;
import com.imesh.lab.models.UserModel;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationService {
    private static AuthenticationService authenticationService;

    public static synchronized AuthenticationService getService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    private AuthenticationDao getAuthenticationDao() {
        return new AuthenticationDaoImpl();
    }

    public CommonMessageModel registerCustomer(UserModel userModel) throws ClassNotFoundException, SQLException {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(regex);
        Matcher emailMatcher = emailPattern.matcher(userModel.getEMail());
        Pattern phonePattern = Pattern.compile("[^\\d+]");
        Matcher phoneMatcher = phonePattern.matcher(userModel.getPhoneNumber());
        if(userModel.getFirstName().length() > 30 || userModel.getFirstName().isEmpty()){
            return new CommonMessageModel("Invalid First Name", false);
        } else if (userModel.getLastName().length() > 50 || userModel.getLastName().isEmpty()) {
            return new CommonMessageModel("Invalid Last Name", false);
        } else if(!emailMatcher.matches()){
            return new CommonMessageModel("Invalid E-mail", false);
        } else if(userModel.getPhoneNumber().length() > 15 || userModel.getPhoneNumber().length() < 6 || phoneMatcher.find()){
            return new CommonMessageModel("Invalid Phone Number", false);
        } else if (!(userModel.getPassword().length() >= 6 && userModel.getPassword().length() <= 24)){
            return new CommonMessageModel("Password length must be between 6 and 24 characters.", false);
        } else if(!userModel.getPassword().matches(".*[A-Z].*") || !userModel.getPassword().matches(".*\\d.*")){
            return new CommonMessageModel("Password doesn't meet the criteria: must contain at least one uppercase letter and one number.", false);
        }else if(!userModel.getPassword().equals(userModel.getConfirmPassword())){
            return new CommonMessageModel("Password doesn't match", false);
        } else {
            getAuthenticationDao().registerCustomer(userModel);
            return new CommonMessageModel("We have sent you an E-mail. Please check your email for the login information.", true);
        }
    }

    public int generateUserCode() throws ClassNotFoundException, SQLException {
        return getAuthenticationDao().generateUserCode();
    }
}
