package th.pt.black_tea.utilities;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.ObjectFactory;
import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.util.Collections;

public class TaxInvoiceXMLGenerator {
    private ObjectFactory factory = new ObjectFactory();


    private static TaxInvoiceXMLGenerator generator = null;

    private TaxInvoiceXMLGenerator() {
    }

    public static TaxInvoiceXMLGenerator getInstance() {
        if (generator == null)
            generator = new TaxInvoiceXMLGenerator();
        return generator;
    }

    public OutputStream generate(TaxInvoiceCrossIndustryInvoiceType taxInvoice, OutputStream stream) {
        JAXBElement<TaxInvoiceCrossIndustryInvoiceType> jaxB = factory.createTaxInvoiceCrossIndustryInvoice(taxInvoice);
        OutputStream signedStream = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TaxInvoiceCrossIndustryInvoiceType.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(jaxB, stream);

            DOMResult dom = new DOMResult();
            jaxbMarshaller.marshal(jaxB, dom);
            signedStream = sign(dom);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return signedStream;
    }

    private OutputStream sign(DOMResult dom) {
        StreamResult result = new StreamResult(System.out);


        try {
            String providerName = System.getProperty("jsr105Provider"
                    , "org.jcp.xml.dsig.internal.dom.XMLDSigRI");

            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM"
                    , (Provider) Class.forName(providerName).newInstance());

            Reference ref = fac.newReference(""
                    , fac.newDigestMethod(DigestMethod.SHA1, null)
                    , Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (XMLStructure) null))
                    , null
                    , null);

            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, (XMLStructure) null)
                    , fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", null)
                    , Collections.singletonList(ref));

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();

            KeyInfoFactory kif = fac.getKeyInfoFactory();
            KeyValue kv = kif.newKeyValue(kp.getPublic());

            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

            Document doc = (Document) dom.getNode();

            DOMSignContext dsc = new DOMSignContext(kp.getPrivate()
                    , doc.getDocumentElement());

            XMLSignature signature = fac.newXMLSignature(si, ki);
            signature.sign(dsc);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(dom.getNode());
            t.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.getOutputStream();
    }

}
