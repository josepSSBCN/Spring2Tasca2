/*!
  * IT Academy Backend JAVA S05T02N01
  * Author  => Josep Salgado Salichs
  *
  */

//#region CONSTANTS

//#endregion CONSTANTS


//#region VARIABLES

//#endregion VARIABLES


//#region OPEN WINDOWS
function OpenLogin() {
  window.open("http://localhost:9011/user/auth/loginFrm", "_self");
}

function OpenNewUser() {
  window.open("http://localhost:9011/user/auth/newUserFrm", "_self");
}

//#endregion OPEN WINDOWS


//#region MAIN FUNCTIONS
function HomeViewOnload(){
    SelectMySQLDDBB();
    /*
    console.log("Hola des de 32");
    SaveDDBBType("mysql");
    var temp = GetDDBBType();
    console.log(temp);
*/
}

function SelectMySQLDDBB(){
    SaveDDBBType("mysql");
}

function SelectMongoDDBB(){
    SaveDDBBType("mongo");
}

//#endregion MAIN FUNCTIONS


//#region AUXILIAR FUNCTIONS

//#endregion AUXILIAR FUNCTIONS
