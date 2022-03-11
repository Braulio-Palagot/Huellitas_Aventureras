package org.equiposeis.huellitasaventureras.dataModels

data class Mascota(
        var id_persona: String = "",
        var nombre_mascota:String = "",
        var edad_mascota:Int = 0,
        var fecha_mascota: String="",
        var raza:String = "",
        var Otraraza:String="",
)
