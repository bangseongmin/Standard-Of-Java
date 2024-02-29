package chapter08.try_with_resource;

public class TryWithResourceEx {

    public static void main(String[] args) {
        try (
                CloseableResource cr = new CloseableResource()
        ) {
            cr.exceptionWork(false);        // 예외가 발생하지 않는다.
        } catch (WorkException e) {
            e.printStackTrace();
        } catch (CloseException e) {
            e.printStackTrace();
        }

        System.out.println();

        try (CloseableResource cr = new CloseableResource()) {
            cr.exceptionWork(true);
        } catch (WorkException e) {
            e.printStackTrace();
        } catch (CloseException e) {
            e.printStackTrace();
        }
    }
}
