package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

public class ExceptionUserInfoNoValid extends Exception{
    public ExceptionUserInfoNoValid(){
        super(Utils.userNameSendNoValid);
    }
}
