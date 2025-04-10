//número de peticiones

setTimeout(async () => {
    let n=500;
    for(let i=0; i<=n;i++){
        let headersList = {
            "Accept": "*/*",
            "User-Agent": "Thunder Client (https://www.thunderclient.com)"
        }
    
        let response = await fetch("http://localhost:8080/api/v1/assistants/", {
            method: "POST",
            headers: headersList
        });
    
        let data = await response.text();
        console.log(data);
        
    }
     
}, 1000); // 2000 milisegundos = 2 segundos
