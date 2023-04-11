package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

public class ExceptionPlayErrorSave extends Exception{
    public ExceptionPlayErrorSave(){
        super(Utils.playErrorWhenSave);
    }
}
