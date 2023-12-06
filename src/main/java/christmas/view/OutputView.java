package christmas.view;

public class OutputView {
    private static final String EXCEPTION_FORMAT = "[ERROR] %s 다시 입력해 주세요.";

    public void printExceptionMessage(String message) {
        println(String.format(EXCEPTION_FORMAT, message));
    }

    private void println(String message) {
        System.out.println(message);
    }

    public void printPreviewMessage() {
        println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
        printEmptyLine();
    }

    private void printEmptyLine() {
        System.out.println();
    }

    public void printStartMessage() {
        println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
}
