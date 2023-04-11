/*
  * IT Academy Backend JAVA S05T02N01
  * Author  => Josep Salgado Salichs
  *
*/


//#region GET FUNCTIONS
// typeIn = mysql if ddbb selected is MySQL type
// typeIn = mongo if ddbb selected is Mongo type
function GetDDBBType(){
    return localStorage.getItem("ddbbtype");
}

function GetToken(){
    return localStorage.getItem("token");
}

function GetTotalPlays(){
    return localStorage.getItem("totalPlays");
}

function GetUserDataSignUp(){
    return localStorage.getItem("dataSignUp");
}

function GetUserId(){
    return localStorage.getItem("userID");
}

function GetUserName(){
    return localStorage.getItem("userName");
}

function Getx100Vict(){
    return localStorage.getItem("x100Vict");
}

function GetWonPlays(){
    return localStorage.getItem("wonPlays");
}

//#endregion GET FUNCTIONS


//#region SAVE FUNCTIONS
// typeIn = mysql if ddbb selected is MySQL type
// typeIn = mongo if ddbb selected is Mongo type
function SaveDDBBType(typeIn){
    localStorage.setItem("ddbbtype", typeIn);
}

function SaveCompletUserInfo(userNameIn, dataSignUpIn, victorx100In, playsGlblIn, wonPlaysGlblIn){
    if(userNameIn !== ""){
        localStorage.setItem("userName", userNameIn);
    }
    if(dataSignUpIn !== ""){
        localStorage.setItem("dataSignUp", dataSignUpIn);
    }
    if(victorx100In !== ""){
        Savex100Vict(victorx100In);
    }
    if(playsGlblIn !== ""){
        SaveTotalPlays(playsGlblIn);
    }
    if(wonPlaysGlblIn !== ""){
        SetWonPlays(wonPlaysGlblIn);
    }
}

function SaveBasicUserInfo(userNameIn, dataSignUpIn){
    if(userNameIn !== ""){
        localStorage.setItem("userName", userNameIn);
    }
    if(dataSignUpIn !== ""){
        localStorage.setItem("dataSignUp", dataSignUpIn);
    }
}

function SaveToken(tokenIn){
    localStorage.setItem("token", tokenIn);
}

function SaveTotalPlays(totalPlaysIn){
    localStorage.setItem("totalPlays", totalPlaysIn);
}

function Savex100Vict(x100VictIn){
    localStorage.setItem("x100Vict", x100VictIn);
}

function SetWonPlays(wonPlaysIn){
    localStorage.setItem("wonPlays", wonPlaysIn);
}

//#endregion SAVE FUNCTIONS


