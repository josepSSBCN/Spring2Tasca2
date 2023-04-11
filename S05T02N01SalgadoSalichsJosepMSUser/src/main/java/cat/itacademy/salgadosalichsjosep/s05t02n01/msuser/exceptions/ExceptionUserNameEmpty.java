package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

public class ExceptionUserNameEmpty extends Exception{
    public ExceptionUserNameEmpty(){
        super(Utils.userNameSendNoValid);
    }

}
