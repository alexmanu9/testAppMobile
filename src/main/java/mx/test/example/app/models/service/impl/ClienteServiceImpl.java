package mx.test.example.app.models.service.impl;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.test.example.app.models.dao.IClienteDao;
import mx.test.example.app.models.entity.Organizaciones;
import mx.test.example.app.service.IClienteService;


@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Organizaciones> findAll() {
		return (List<Organizaciones>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Organizaciones cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Organizaciones findOne(Long id) {
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.delete(id);
		
	}
	
	public String generaIdExterno(String nombre, String telefono) {

		String cadenaUno = nombre.substring(0, 3);
		String cadenaDos = telefono.substring((telefono.length() - 4), (telefono.length()));
		String devuelve = cadenaUno + cadenaDos + "NE" + generaAleatorioConsecutivo();

		return devuelve;
	}

	public String generaAleatorioConsecutivo() {

		Integer numero = (int) (Math.random() * 10 + 1);
		Integer num2 = numero + 1;
		Integer num3 = num2 + 1;

		String devuelve = numero.toString() + num2.toString() + num3.toString();

		return devuelve;
	}
	
	public String codigoEncriptacion(String texto) {

        String secretKey = "miPrueba";
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.getEncoder().encode(buf);
            base64EncryptedString = new String(base64Bytes);
            base64EncryptedString= base64EncryptedString.substring(0,7);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
}
	
	

}
