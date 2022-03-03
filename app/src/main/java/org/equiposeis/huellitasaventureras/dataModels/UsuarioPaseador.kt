package org.equiposeis.huellitasaventureras.dataModels

class UsuarioPaseador(
    var id_paseador: String = "",
    var capacitacion: String = "",
    var metodo_cobro: String = "",
    nombre:String = "",
    genero:Int = 0,
    edad:Int = 0,
    numero_telefonico:Int = 0,
    domicilio:String = "",
    correo_electronico:String = ""
): Persona(nombre, genero, edad, numero_telefonico, domicilio, correo_electronico)