package th.pt.black_tea.utilities;

import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.ObjectFactory;
import etda.uncefact.data.standard.taxinvoice_crossindustryinvoice._2.TaxInvoiceCrossIndustryInvoiceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.security.x509.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String providerName = System.getProperty("jsr105Provider"
                , "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        String signatureId = UUID.randomUUID().toString();

        try {
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM"
                    , (Provider) Class.forName(providerName).newInstance());
            KeyPair kp = mockKeyPair("RSA", 1024);
            X509Certificate cert = mockCertificate("CN=www.fuckyou.com", kp, 256, "SHA512withRSA");
            List<XMLObject> properties = mockSignedProperties((Document) dom.getNode(), fac, signatureId);

            Reference ref = fac.newReference(""
                    , fac.newDigestMethod("http://www.w3.org/2001/04/xmlenc#sha512", null)
                    , Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (XMLStructure) null))
                    , null
                    , null);

            SignedInfo si = fac.newSignedInfo(
                    fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (XMLStructure) null)
                    , fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", null)
                    , Collections.singletonList(ref));

            KeyInfoFactory kif = fac.getKeyInfoFactory();
            List<Object> x509Content = new ArrayList<Object>();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);
            X509Data cerData = kif.newX509Data(x509Content);
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(cerData), null);

            Document doc = (Document) dom.getNode();

            DOMSignContext dsc = new DOMSignContext(kp.getPrivate()
                    , doc.getDocumentElement());

            XMLSignature signature = fac.newXMLSignature(si, ki, properties, signatureId, null);
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

    private KeyPair mockKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = null;
        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);

        return keyPairGenerator.generateKeyPair();
    }

    private X509Certificate mockCertificate(String dn, KeyPair pair, int days, String algorithm)
            throws GeneralSecurityException, IOException {
        PrivateKey privkey = pair.getPrivate();
        X509CertInfo info = new X509CertInfo();
        Date from = new Date();
        Date to = new Date(from.getTime() + days * 86400000l);
        CertificateValidity interval = new CertificateValidity(from, to);
        BigInteger sn = new BigInteger(64, new SecureRandom());
        X500Name owner = new X500Name(dn);

        info.set(X509CertInfo.VALIDITY, interval);
        info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
        info.set(X509CertInfo.SUBJECT, owner);
        info.set(X509CertInfo.ISSUER, owner);
        info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
        info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
        info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

        // Sign the cert to identify the algorithm that's used.
        X509CertImpl cert = new X509CertImpl(info);
        cert.sign(privkey, algorithm);

        // Update the algorith, and resign.
        algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
        info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
        cert = new X509CertImpl(info);
        cert.sign(privkey, algorithm);
        return cert;
    }

    private List<XMLObject> mockSignedProperties(Document dom, XMLSignatureFactory fac, String signatureId) {
        String signaturePropertyId = UUID.randomUUID().toString();

        // Contenido de SignatureProperty
        Element content = dom.createElement("dc:date");
        content.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SS");
        content.setTextContent(sdf.format(new Date()));
        XMLStructure str = new DOMStructure(content);
        List<XMLStructure> contentList = new ArrayList<XMLStructure>();
        contentList.add(str);

        // SignatureProperty
        SignatureProperty sp = fac.newSignatureProperty(contentList, "#" + signatureId,
                signaturePropertyId);
        List<SignatureProperty> spList = new ArrayList<SignatureProperty>();
        spList.add(sp);

        // SignatureProperties
        SignatureProperties sps = fac.newSignatureProperties(spList, null);
        List<SignatureProperties> spsList = new ArrayList<SignatureProperties>();
        spsList.add(sps);

        // Object
        XMLObject object = fac.newXMLObject(spsList, null, null, null);
        List<XMLObject> objectList = new ArrayList<XMLObject>();
        objectList.add(object);

        return objectList;
    }

}
