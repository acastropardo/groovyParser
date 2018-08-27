import groovy.xml.StreamingMarkupBuilder
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*
import groovy.json.*


def Message processData(Message message) {
    //Body 
       //def body = message.getBody();
       //def body = message.getBody(String.class);
       def body = message.getBody(java.io.Reader)
       //def body = message.getBody(java.lang.String)
       
		def idOferta
		def results = new JsonSlurper().parse(body)
		def writer = new StringWriter()
		def builder = new groovy.xml.MarkupBuilder(writer)
		def helper = new groovy.xml.MarkupBuilderHelper(builder)
//def builder = new StreamingMarkupBuilder(writer)



		helper.xmlDeclaration([version:'1.0', encoding:'UTF-8'])


		builder.datosOfertas {

		for (ofertas in results.mapeoProcesoFin.datosOfertas) {


		 	if ( ofertas.EstadoRegistro == null && ofertas.IDUsuario == null && ofertas.HoraIniSegmento==null && ofertas.HoraFinSegmento==null ){ //indices ID Oferta

		 			for (empleado in results.mapeoProcesoFin.datosOfertas) {


					 	if ( (empleado.EstadoRegistro != null && empleado.EstadoRegistro == 'ENROLL ' ) && empleado.IDOfertaProg == ofertas.IDOfertaProg ){ //empleados enrolados
					 		
					 		for (segmento in results.mapeoProcesoFin.datosOfertas) {
							 	
								if (  segmento.IDOfertaProg == ofertas.IDOfertaProg &&  ( segmento.HoraIniSegmento != null  && segmento.HoraFinSegmento != null ) ) { //segmentos de horario de la misma oferta
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

					}
					
			}

		}
		}

		}
		
	   def resultadoTXT = writer.toString()

       def xmlOut = new XmlParser().parseText(resultadoTXT)
       

       message.setBody(xmlOut);
       
       def xml = XmlUtil.serialize(xmlOut);
	    
	    message.setBody(xml); 
    
       
       return message;
}