function getLocation(bus_id){
    fetch('find-bus',{
        method:"get",
        headers:{"Conent-type":"application/json"},
        body:JSON.stringify({id:bus_id})
    })
    .then((respomse)=>{
        if(!respomse.ok){
            throw new Error("HTTP-Error: " + respomse.status);
        }

        return respomse.json();
    })
    .catch((error)=>{
        console.error("error");
    });
}