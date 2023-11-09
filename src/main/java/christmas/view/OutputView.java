package christmas.view;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";

    public void printExceptionMessage(String message) {
        String exceptionMessage = String.format(EXCEPTION_FORMAT, message);
        System.out.println(exceptionMessage);
    }

    public void printWelcomeMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
}
