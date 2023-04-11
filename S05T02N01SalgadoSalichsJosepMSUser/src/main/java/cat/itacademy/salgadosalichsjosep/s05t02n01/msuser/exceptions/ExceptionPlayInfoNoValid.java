package cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.exceptions;

import cat.itacademy.salgadosalichsjosep.s05t02n01.msuser.utils.Utils;

public class ExceptionPlayInfoNoValid extends Exception{
    public ExceptionPlayInfoNoValid(){
        super(Utils.playInfoNoValid);
    }
}
