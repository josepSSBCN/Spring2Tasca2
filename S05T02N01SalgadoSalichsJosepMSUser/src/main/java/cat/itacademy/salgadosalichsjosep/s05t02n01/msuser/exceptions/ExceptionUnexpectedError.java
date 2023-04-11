package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

public class ExceptionUnexpectedError extends Exception{
    public ExceptionUnexpectedError(String classThrowIn, String methodThrowIn){
        super("CLASS THROW: " + classThrowIn + "; METHOD THROW: " + methodThrowIn);
    }
}
