package org.beans;

import utils.EmailSender;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Random;
import java.util.stream.Collectors;

@ManagedBean(name = "sendBean")
@ApplicationScoped
public class SendMsgBean implements Serializable {
    private static final long serialVersionUID = 2;
    private static final String senderLog = "profi181196@gmail.com";
    private static final String senderPassword = "p1811963365191";
    private String toEmail;
    private static final String subject = "Подтверждающий код при регистрации";
    private static final String mainText = "Ваш подтверждающий код : ";
    private String confirmCode;
    private String inputCode;

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getMainText() {
        return mainText;
    }

    public String send(){
        EmailSender sender = new EmailSender(senderLog, senderPassword);
        String confirmCode = new Random().ints(6, 0, 10).
                mapToObj(Integer::toString).collect(Collectors.joining());
        this.confirmCode = confirmCode;
        System.out.println("Code : " + confirmCode);
        sender.send(subject, mainText.concat(confirmCode), senderLog, toEmail);
        return "confirm-code";
    }

    public String sendRedirectIfOk(){
        System.out.println("Redirecting");
        return (inputCode.equals(confirmCode) ? "/mains/mp" : "reg").concat("?faces-redirect=true");
    }

    @Override
    public String toString() {
        return "SendMsgBean{" +
                "toEmail='" + toEmail + '\'' +
                ", confirmCode='" + confirmCode + '\'' +
                '}';
    }
}
