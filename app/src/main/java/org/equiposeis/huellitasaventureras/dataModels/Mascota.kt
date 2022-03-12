package org.equiposeis.huellitasaventureras.dataModels

data class Mascota(
        var id_usuario: String = "",
        var nombre_mascota:String = "",
        var edad_mascota:Int = 0,
        var fecha_mascota: String="",
        var raza:String = "",
)
