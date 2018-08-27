import groovy.xml.StreamingMarkupBuilder

import groovy.xml.*

def xmlSource = new File('payloadParseroCandidato.xml')
/*
def groovyMoviez = '''
<?xml version="1.0" encoding="UTF-8"?>
<mapeoProceso>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
        <IDUsuario>20002985</IDUsuario>
        <EstadoRegistro>ENROLL </EstadoRegistro>
    </datosOfertas>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
        <IDUsuario>20002417</IDUsuario>
        <EstadoRegistro>ENROLL </EstadoRegistro>
    </datosOfertas>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
        <IDUsuario>20002999</IDUsuario>
        <EstadoRegistro>ENROLL </EstadoRegistro>
    </datosOfertas>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
        <IDUsuario>30002792</IDUsuario>
        <EstadoRegistro>ENROLL </EstadoRegistro>
    </datosOfertas>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
    </datosOfertas>
    <datosOfertas>
        <IDOfertaProg>5048</IDOfertaProg>
        <HoraIniSegmento>20/sep/2018 09:00 AM América/Santiago</HoraIniSegmento>
        <HoraFinSegmento>20/sep/2018 05:00 PM América/Santiago</HoraFinSegmento>
    </datosOfertas>
</mapeoProceso>
'''
*/

/*
def results = new XmlSlurper().parse(xmlSource)

//def results = new XmlSlurper().parseText(groovyMoviez)

for (flick in results.datosOfertas) {
 	if ( flick.EstadoRegistro == '' && flick.IDUsuario == '' && flick.HoraIniSegmento=='' && flick.HoraFinSegmento=='' ){
 		println "ID Oferta: ${flick.IDOfertaProg}"
	}

}


for (flick in results.datosOfertas) {
 	if (flick.EstadoRegistro == 'ENROLL '){
 		println "Oferta id ${flick.IDOfertaProg} tiene enrolado el empleado ${flick.IDUsuario}"
	}
	if (flick.HoraIniSegmento != ''){
 		println "Oferta id ${flick.IDOfertaProg} segmento ${flick.HoraIniSegmento} a ${flick.HoraFinSegmento}"
	}

}

println('*************************************************************************************')
*/

def idOferta
def results = new XmlSlurper().parse(xmlSource)
def writer = new StringWriter()
def builder = new groovy.xml.MarkupBuilder(writer)
def helper = new groovy.xml.MarkupBuilderHelper(builder)
//def builder = new StreamingMarkupBuilder(writer)

helper.xmlDeclaration([version:'1.0', encoding:'UTF-8', standalone:'no'])


builder.datosOfertas {



for (ofertas in results.datosOfertas) {
 	if ( ofertas.EstadoRegistro == '' && ofertas.IDUsuario == '' && ofertas.HoraIniSegmento=='' && ofertas.HoraFinSegmento=='' ){ //indices ID Oferta
 		//idOferta = ofertas.IDOfertaProg


 			for (empleado in results.datosOfertas) {
			 	if (empleado.EstadoRegistro == 'ENROLL ' && empleado.IDOfertaProg == ofertas.IDOfertaProg ){ //empleados enrolados
			 		
			 		for (segmento in results.datosOfertas) {
					 	
						if (  segmento.IDOfertaProg == ofertas.IDOfertaProg &&  ( segmento.HoraIniSegmento != ''  && segmento.HoraFinSegmento != '' ) ) { //segmentos de horario de la misma oferta
					 		builder.inscrito 
					 		{
					 			idOfertaProgramada(ofertas.IDOfertaProg)
					 			IDUsuarioInscrito(empleado.IDUsuario)
					 			FechaIniSegmento(segmento.FechaIniSegmento)
							 	FechaFinSegmento(segmento.FechaFinSegmento)
					 			HoraIniSegmento(segmento.HoraIniSegmento)
					 			HoraFinSegmento(segmento.HoraFinSegmento)
							}

						}

					}

					
			 		//println "Oferta id ${flick.IDOfertaProg} tiene enrolado el empleado ${flick.IDUsuario}"
				
				//println salida
				//println writer.toString()
				
				
			}
			
	}

}
}

}


def resultadoTXT = writer.toString()

//def xmlOut = new groovy.xml.XMLParser().parseText(resultadoTXT)

def root = new XmlParser().parseText(resultadoTXT);

def xml = XmlUtil.serialize(root);

println xml



/*
for (flick in results.movie) {
 println "Movie with id ${flick.@id} " + "is directed by ${flick.director}"
}

println()
println()

results.movie*.title.each { println "- ${it}" }
results.movie.findAll {
 it.year.toInteger() > 1990
}*.title.each {
 println "title: ${it}"
}

println()
println()

results.movie.findAll {
 it.director.text().contains('Milo')
}.each {
 println "- ${it.title}"
}
*/