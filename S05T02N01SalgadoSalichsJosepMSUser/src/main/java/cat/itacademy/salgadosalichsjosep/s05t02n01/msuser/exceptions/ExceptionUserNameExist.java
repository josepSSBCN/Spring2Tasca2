package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

public class ExceptionUserNameExist extends Exception{
    public ExceptionUserNameExist(){
        super(Utils.playErrorWhenSave);
    }
}
