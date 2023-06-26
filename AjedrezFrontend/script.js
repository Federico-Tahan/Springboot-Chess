let juegos = [];

$("#CargarPartida").on("click", async function () {
  


  await obtenerJuegos(); 

  let container = document.getElementById("cardContainer");

  $("#cardContainer").empty();
  for (let i = 0; i < juegos.length; i++) {
    const item = juegos[i];

    let card = document.createElement("div");
    card.classList.add("card");
    card.classList.add("cardGuardado");

    card.style.width = "18rem";
    card.style.marginBottom = "5%";
    card.style.marginLeft = "16%";

    let body = document.createElement("div");
    body.classList.add("card-body");

    let h5 = document.createElement("h5");
    h5.classList.add("card-title");
    h5.textContent = item.id;


    card.addEventListener("click", async function ()
    {
      await establecerJuego(item.id);
    })
  



    body.appendChild(h5);
    card.appendChild(body);
    container.appendChild(card);
  }
});




async function establecerJuego(idJuego) {
  const url = `http://localhost:8080/establecerJuego?idJuego=${idJuego}`;

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    const data = await response.json();
    if (data === true) {
      alert("Peticion aceptada, el juego se establecerá");
      window.location.href = 'Partida.html';
    }
  } catch (error) {
    console.error('Error:', error);
  }
}










async function obtenerJuegos() {
  const url = 'http://localhost:8080/obtenerJuegos';

  try {
    const response = await fetch(url);
    const data = await response.json();

    juegos.length = 0;

    juegos = data;
    console.log(juegos); 
  } catch (error) {
    console.error('Error:', error);
  }
}




























async function enviarPeticion(nombreblanco, nombrenegro) {
    const url = 'http://localhost:8080/crearNuevoJuego';
    const params = new URLSearchParams();
    params.append('nombreBlanco', nombreblanco);
    params.append('nombreNegro', nombrenegro);
  
    try {
      const response = await fetch(`${url}?${params.toString()}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });
  
      const data = await response.json();
      if(data == true){
        alert("Peticion aceptada, la partida se creará");
        window.location.href ='Partida.html';

      }

    } catch (error) {
      console.error('Error:', error);
    }
  }


 


  


