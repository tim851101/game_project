let myKey="f1688d3469e422af82cc95be8c05cdee";
let city="Taoyuan";
let url=`https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${myKey}`;

async function weather(){
    try{
        let result=await fetch(url);
        let data=await result.json();
        console.log(data);
    }catch(e){
        console.log(e);
    }
}
weather();