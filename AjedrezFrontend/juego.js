let tablero = [];
let gameStatus = [];

document.addEventListener('DOMContentLoaded', async function () {
    console.log('La página se ha cargado completamente');
    await obtenerEstadoDelJuego();
    await traerTablero();
    await miFuncion();
    colocarNombres();
    iniciarCronometro();
    cargarMovimientos();



    if(!gameStatus.enCurso)
    {
        let btnTabla = document.getElementById("btnOfrecerTabla");
        btnTabla.style.display= "none";
        pausarCronometros();
        pausarCronometro();
    }

});

async function limpiarTablero() {
    try {
        for (let i = 0; i < 8; i++) {
            for (let j = 0; j < 8; j++) {

                const contenedorEl = document.getElementById(i.toString() + j.toString());

                const childNodes = contenedorEl.childNodes;
                for (let k = childNodes.length - 1; k >= 0; k--) {
                    const childNode = childNodes[k];
                    if (childNode.tagName !== 'P') {
                        contenedorEl.removeChild(childNode);
                    }
                }

            }
        }
    } catch (error) {
    }
}

async function miFuncion() {
    for (let i = 0; i < tablero.length; i++) {
        for (let j = 0; j < tablero[i].length; j++) {
            const item = tablero[i][j];

            if (item != null) {
                let div = document.getElementById(item.fila.toString() + item.columna.toString());

                let img = document.createElement("img");

                let ruta;
                switch (item.nombre) {
                    case 'Torre':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/rook.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/rook.png';
                        }
                        break;
                    case 'Caballo':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/knight.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/knight.png';
                        }
                        break;
                    case 'Alfil':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/bishop.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/bishop.png';
                        }
                        break;
                    case 'Reina':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/queen.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/queen.png';
                        }
                        break;
                    case 'Rey':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/king.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/king.png';
                        }
                        break;
                    case 'Peon':
                        if (item.color === 'blanco') {
                            ruta = './images/pieces/white/pawn.png';
                        } else if (item.color === 'negro') {
                            ruta = './images/pieces/black/pawn.png';
                        }
                        break;
                    default:
                        ruta = './images/pieces/pawn.png';
                        break;
                }

                img.src = ruta;
                img.classList.add("clase-cursor");
                img.style.position = "absolute";
                img.style.height = "100%";
                img.style.width = "100%";

                let estaSeleccionado = false;


                let elementos = document.querySelectorAll('.backGrounReyJaque');

                elementos.forEach(function (elemento) {
                    elemento.classList.remove('backGrounReyJaque');
                });

                if (gameStatus.reyJaqueado != null) {

                    let divRey = document.getElementById(gameStatus.reyJaqueado.fila.toString() + gameStatus.reyJaqueado.columna.toString());

                    divRey.classList.add("backGrounReyJaque");

                }






                if (gameStatus.enCurso) {
                    img.addEventListener("click", function () {

                        if (gameStatus.turnoBlanco && item.color == "blanco") {

                            const tieneCirculo = div.getElementsByClassName("circulo-grisacio").length > 0;

                            const circulos = document.getElementsByClassName("circulo-grisacio");
                            while (circulos.length > 0) {
                                circulos[0].parentNode.removeChild(circulos[0]);
                            }
                            const seleccionado = document.getElementsByClassName("backGrounSeleccionado");
                            while (seleccionado.length > 0) {
                                seleccionado[0].classList.remove("backGrounSeleccionado");
                            }

                            div.classList.add("backGrounSeleccionado");

                            if (!tieneCirculo) {
                                estaSeleccionado = !estaSeleccionado;

                                for (let i = 0; i < item.movsPosibles.length; i++) {
                                    const filaMovimiento = item.movsPosibles[i].fila.toString();
                                    const columnaMovimiento = item.movsPosibles[i].columna.toString();
                                    console.log(filaMovimiento + "" + columnaMovimiento);
                                    const divMovimiento = document.getElementById(filaMovimiento + columnaMovimiento);

                                    const divCirculo = document.createElement("div");
                                    divCirculo.classList.add("circulo-grisacio");
                                    divCirculo.classList.add("clase-cursor");
                                    divCirculo.setAttribute("id", filaMovimiento + columnaMovimiento);

                                    divMovimiento.appendChild(divCirculo);


                                    const filaItem = item.fila;
                                    const columnaItem = item.columna;

                                    divCirculo.addEventListener("click", async function () {
                                        const circuloID = this.getAttribute("id");
                                        const destinoFila = circuloID.charAt(0);
                                        const destinoColumna = circuloID.charAt(1);
                                        await movimientoPieza(filaItem, columnaItem, destinoFila, destinoColumna);
                                        const seleccionado = document.getElementsByClassName("backGrounSeleccionado");
                                        while (seleccionado.length > 0) {
                                            seleccionado[0].classList.remove("backGrounSeleccionado");
                                        }
                                    });
                                }
                            }

                        } else if (!gameStatus.turnoBlanco && item.color == "negro") {
                            const tieneCirculo = div.getElementsByClassName("circulo-grisacio").length > 0;

                            const circulos = document.getElementsByClassName("circulo-grisacio");
                            while (circulos.length > 0) {
                                circulos[0].parentNode.removeChild(circulos[0]);
                            }
                            const seleccionado = document.getElementsByClassName("backGrounSeleccionado");
                            while (seleccionado.length > 0) {
                                seleccionado[0].classList.remove("backGrounSeleccionado");
                            }

                            div.classList.add("backGrounSeleccionado");


                            if (!tieneCirculo) {
                                estaSeleccionado = !estaSeleccionado;

                                for (let i = 0; i < item.movsPosibles.length; i++) {
                                    const filaMovimiento = item.movsPosibles[i].fila.toString();
                                    const columnaMovimiento = item.movsPosibles[i].columna.toString();
                                    console.log(filaMovimiento + "" + columnaMovimiento);
                                    const divMovimiento = document.getElementById(filaMovimiento + columnaMovimiento);

                                    const divCirculo = document.createElement("div");
                                    divCirculo.classList.add("circulo-grisacio");
                                    divCirculo.classList.add("clase-cursor");
                                    divCirculo.setAttribute("id", filaMovimiento + columnaMovimiento);

                                    divMovimiento.appendChild(divCirculo);


                                    const filaItem = item.fila;
                                    const columnaItem = item.columna;

                                    divCirculo.addEventListener("click", async function () {
                                        const circuloID = this.getAttribute("id");
                                        const destinoFila = circuloID.charAt(0);
                                        const destinoColumna = circuloID.charAt(1);
                                        await movimientoPieza(filaItem, columnaItem, destinoFila, destinoColumna);
                                        const seleccionado = document.getElementsByClassName("backGrounSeleccionado");
                                        while (seleccionado.length > 0) {
                                            seleccionado[0].classList.remove("backGrounSeleccionado");
                                        }

                                    });
                                }
                            }


                        }

                    });
                } else {
                    let labelEstadoPartida = document.getElementById("EstadoPartida");
                    labelEstadoPartida.textContent = "Partida Finalizada";
                }

                div.appendChild(img);


            }

        }
    }
}
/*
async function iniciarTablero() {
    const url = 'http://localhost:8080/iniciarTablero';

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            console.log('Tablero iniciado correctamente');
        } else {
            console.error('Error al iniciar el tablero');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
*/

async function traerGameStatus() {
    const url = 'http://localhost:8080/gameStatus';

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {

            gameStatus.length = 0;
            gameStatus = await response.json();

        } else {
            console.error('Error al iniciar el tablero');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function movimientoPieza(origenF, origenC, destinoF, destinoC) {
    const url = 'http://localhost:8080/movimiento';

    try {
        const response = await fetch(`${url}?origenF=${origenF}&origenC=${origenC}&destinoF=${destinoF}&destinoC=${destinoC}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();

            gameStatus.length = 0;
            gameStatus = data;
            tablero.length = 0;

            if (gameStatus.movAprobado) {
                await traerTablero();
                await limpiarTablero();
                await miFuncion();
                coronarPeon(gameStatus);
                reproducirSonido();
                await cargarMovimientos();

                if(gameStatus.turnoBlanco)
                {
                    iniciarCronometro();
                    pausarCronometros();
                }else{
                    iniciarCronometros();
                    pausarCronometro();
                }

                terminar();


                console.log(gameStatus);
            } else {
                console.log("No se ha podido realizar el movimiento, intentelo de nuevo mas tarde.");
            }

        } else {
            console.error('Error al realizar el movimiento');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function traerTablero() {
    const url = 'http://localhost:8080/mostrarPiezas';

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        let data = await response.json();
        if (response.ok) {
            tablero = data;
        } else {
            console.error('Error al iniciar el tablero');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}


function reproducirSonido() {
    const audio = new Audio('images/sonidomov.mp3');
    audio.play();
}

async function coronarPeonpeticion(ficha) {
    const url = 'http://localhost:8080/cambiarFicha';

    try {
        const response = await fetch(`${url}?nuevaFicha=${ficha}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        let data = await response.json();
        if (response.ok) {

            $('#staticBackdrop').modal('hide');
            await traerGameStatus();
            await traerTablero();
            await limpiarTablero();
            await miFuncion();



        } else {
            console.error('Error al iniciar el tablero');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function obtenerEstadoDelJuego() {
    try {
        const response = await fetch('http://localhost:8080/gameStatus');
        const data = await response.json();
        gameStatus.length = 0;
        gameStatus = data;
    } catch (error) {
        console.error('Error:', error);
    }
}






function coronarPeon(gn) {
    if (gn.coronarPeon) {
        $('#staticBackdrop').modal('show');
    }

}


function terminar() {

    if (gameStatus.jaqueMate) {
        let msgWin = document.getElementById("nomGanadorPartida");
        let btnTabla = document.getElementById("btnOfrecerTabla");
        btnTabla.style.display= "none";
        if(gameStatus.reyJaqueado.color == "negro")
        {
            msgWin.textContent = gameStatus.nameJugadorBlanco + " ganaste la partida!! 🏆"
        }else
        {
            msgWin.textContent = gameStatus.nameJugadorNegro + " ganaste la partida!! 🏆"

        }
        pausarCronometros();
        pausarCronometro();
        $('#modalGanador').modal('show');
    }
}


function terminarEmpate() {

    if (!gameStatus.enCurso) {
        let btnTabla = document.getElementById("btnOfrecerTabla");
        btnTabla.style.display= "none";
        pausarCronometros();
        pausarCronometro();
        $('#modalEmpate').modal('show');

    }
}

async function empatar(rta) {
    const url = `http://localhost:8080/empatar?rtaAdversario=${rta}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            gameStatus.length = 0;
            gameStatus = await response.json();
            await traerTablero();
            await limpiarTablero();
            await miFuncion();

        } else {
            console.error('Error en la solicitud');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}


$("#btnGuardarPartida").on("click", async function () {
    await GuardarPartida();
});

async function GuardarPartida() {
    const url = `http://localhost:8080/GuardarPartida`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            alert("Partida guardada con Exito.");

        } else {
            console.error('Error en la solicitud');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}




$("#coronaCaballo").on("click", async function () {
    await coronarPeonpeticion(1);
});

$("#coronaAlfil").on("click", async function () {
    await coronarPeonpeticion(2);
});

$("#coronaToore").on("click", async function () {
    await coronarPeonpeticion(3);
});

$("#coronaReina").on("click", async function () {
    await coronarPeonpeticion(4);
});




$("#btnOfrecerTabla").on("click", async function () {

    $('#modalOfrecerTabla').modal('show');

});






function cargarMovimientos() {

    let ContainerMovs = document.getElementById("listaMovimientos");

    const filas = document.getElementsByClassName("filasTemporales");
    while (filas.length > 0) {
        filas[0].parentNode.removeChild(filas[0]);
    }

    for (let i = 0; i < gameStatus.listaMovimientos.length; i++) {
        let row = document.createElement("div");
        row.classList.add("row");
        row.classList.add("filasTemporales");

        row.classList.add("align-items-center");

      

        const movimiento = gameStatus.listaMovimientos[i];
        let Movimiento = document.createElement("div");
        Movimiento.classList.add("col");
        Movimiento.textContent = (movimiento.movOrigenFila + 1).toString() + asignarLetra(movimiento.movOrigenCol);

        let Movimiento2 = document.createElement("div");
        Movimiento2.classList.add("col");
        Movimiento2.textContent = (movimiento.movDestinoFila + 1).toString() + asignarLetra(movimiento.movDestinoCol);


        row.appendChild(Movimiento);
        row.appendChild(Movimiento2);

        
        let hr = document.createElement("hr");
        row.appendChild(hr);

        ContainerMovs.appendChild(row);

    }

}


function asignarLetra(numero) {
    const letras = {
        0: 'A',
        1: 'B',
        2: 'C',
        3: 'D',
        4: 'E',
        5: 'F',
        6: 'G',
        7: 'H'
    };

    return letras[numero];
}


var cronometroElement = document.getElementById("cronometro");
var horas = 0;
var minutos = 0;
var segundos = 0;
var intervalo;

function actualizarCronometro() {
    segundos++;
    if (segundos >= 60) {
        segundos = 0;
        minutos++;
        if (minutos >= 60) {
            minutos = 0;
            horas++;
        }
    }

    cronometroElement.textContent = (horas ? (horas > 9 ? horas : "0" + horas) : "00") + ":" +
        (minutos ? (minutos > 9 ? minutos : "0" + minutos) : "00") + "." +
        (segundos > 9 ? segundos : "0" + segundos);
}

function iniciarCronometro() {
    intervalo = setInterval(actualizarCronometro, 1000);
}

function pausarCronometro() {
    clearInterval(intervalo);
}

function reiniciarCronometro() {
    clearInterval(intervalo);
    horas = 0;
    segundos = 0;
    minutos = 0;
    cronometroElement.textContent = "00:00.00";
}




var cronometroElements = document.getElementById("cronometros");
var horass = 0;
var segundoss = 0;
var minutoss = 0;
var intervalos;

function actualizarCronometros() {
    segundoss++;
    if (segundoss >= 60) {
        segundoss = 0;
        minutoss++;
        if (minutoss >= 60) {
            minutoss = 0;
            horass++;
        }
    }

    cronometroElements.textContent = (horass ? (horass > 9 ? horass : "0" + horass) : "00") + ":" +
        (minutoss ? (minutoss > 9 ? minutoss : "0" + minutoss) : "00") + "." +
        (segundoss > 9 ? segundoss : "0" + segundoss);
}

function iniciarCronometros() {
    intervalos = setInterval(actualizarCronometros, 1000);
}

function pausarCronometros() {
    clearInterval(intervalos);
}

function reiniciarCronometros() {
    clearInterval(intervalos);
    horass = 0;
    segundoss = 0;
    minutoss = 0;
    cronometroElements.textContent = "00:00.00";
}




function colocarNombres() {
   let nameJBlanco = document.getElementById("nomJugadorBlanco");
   nameJBlanco.textContent = gameStatus.nameJugadorBlanco;
   let nameJNegro = document.getElementById("nomJugadorNegro");
   nameJNegro.textContent = gameStatus.nameJugadorNegro;
}


$("#aceptarTablas").on("click", async function () {
    await empatar(1);
    terminarEmpate();
    $('#modalOfrecerTabla').modal('hide');

});


$("#CancelarTablas").on("click", function () {
    $('#modalOfrecerTabla').modal('hide');

});

