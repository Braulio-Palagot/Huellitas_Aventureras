package org.equiposeis.huellitasaventureras.dataModels

data class Persona(
    var nombre:String = "",
    var genero:Int = 3,
    var edad:Int = 0,
    var numero_telefonico:Int = 0,
    var domicilio:String = "",
    var correo_electronico:String = ""
)
