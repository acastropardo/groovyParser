import groovy.xml.StreamingMarkupBuilder
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message processData(Message message) {
    //Body 
       //def body = message.getBody();
       def body = message.getBody(String.class);
       //def body = message.getBody(java.io.Reader)
       //def body = message.getBody(java.lang.String)
       
		def idOferta
		def results = new XmlSlurper().parse(body)
		def writer = new StringWriter()
		def builder = new groovy.xml.MarkupBuilder(writer)
		def helper = new groovy.xml.MarkupBuilderHelper(builder)
//def builder = new StreamingMarkupBuilder(writer)



		helper.xmlDeclaration([version:'1.0', encoding:'UTF-8', standalone:'no'])


		builder.datosOfertas {

		for (ofertas in results.datosOfertas) {
		 	if ( ofertas.EstadoRegistro .text() == '' && ofertas.IDUsuario.text() == '' && ofertas.HoraIniSegmento.text() =='' && ofertas.HoraFinSegmento.text() =='' ){ //indices ID Oferta
		 		//idOferta = ofertas.IDOfertaProg


		 			for (empleado in results.datosOfertas) {
					 	if (empleado.EstadoRegistro.text() == 'ENROLL ' && empleado.IDOfertaProg.text() == ofertas.IDOfertaProg.text() ){ //empleados enrolados
					 		
					 		for (segmento in results.datosOfertas) {
							 	
								if (  segmento.IDOfertaProg.text() == ofertas.IDOfertaProg.text() &&  ( segmento.HoraIniSegmento.text() != ''  && segmento.HoraFinSegmento.text() != '' ) ) { //segmentos de horario de la misma oferta
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