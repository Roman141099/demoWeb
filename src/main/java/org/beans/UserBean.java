package org.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.regex.Pattern;

@ManagedBean(name = "user")

@FacesValidator("email_valid")
@SessionScoped
public class UserBean implements Serializable, Validator {
    private static final String template = "[a-zA-Z][\\w.]+?@inbox\\.ru";
    private static final Pattern emailPattern = Pattern.compile(template);
    private static final long serialVersionUID = 1;
    private String name;
    private String login;
    private String password;
    private boolean logged;
    private boolean confCodeSent = true;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doLogin() {
        if(login.equals("") || password.equals("")){
            System.out.println("Неверные данные");
            return;
        }
        logged = true;
        System.out.println("Зарегистрирован " +
                "пользователь с данными : " + login + ", password : " + password);
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String input = o.toString();
        if(!emailPattern.matcher(input).matches()){
            confCodeSent = false;
            FacesMessage msg = new FacesMessage("Не совпадает с шаблоном");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
}
