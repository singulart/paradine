package ua.com.paradine.core.business.vo.commands;

public class CancelIntendedVisitCommand {

    private final String userLogin;
    private final String visit;

    public CancelIntendedVisitCommand(String userLogin, String visit) {
        this.userLogin = userLogin;
        this.visit = visit;
    }

    public String getVisit() {
        return visit;
    }

    public String getUserLogin() {
        return userLogin;
    }
}
