package com.yakut.karabaglar;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;

/**
 *
 * @author yakut
 */
public class Test {

//          public static void main(String[] args) {
//
//
//                    if (args.length != 5) {
//                              System.out.println("usage: java sendfile <to> <from> <smtp> <file> true|false");
//                              System.exit(1);
//                    }
//
//                    String to = "toxema@gmail.com";
//                    String from ="toxema@gmail.com";
//                    String host = "";
//                    String filename = args[3];
//                    boolean debug = Boolean.valueOf(args[4]).booleanValue();
//                    String msgText1 = "Sending a file.\n";
//                    String subject = "Sending a file";
//
//                    // create some properties and get the default Session
//                    Properties props = System.getProperties();
//                    props.put("mail.smtp.host", host);
//
//                    Session session = Session.getInstance(props, null);
//                    session.setDebug(debug);
//
//                    try {
//                              // create a message
//                              MimeMessage msg = new MimeMessage(session);
//                              msg.setFrom(new InternetAddress(from));
//                              InternetAddress[] address = {new InternetAddress(to)};
//                              msg.setRecipients(Message.RecipientType.TO, address);
//                              msg.setSubject(subject);
//
//                              // create and fill the first message part
//                              MimeBodyPart mbp1 = new MimeBodyPart();
//                              mbp1.setText(msgText1);
//
//                              // create the second message part
//                              MimeBodyPart mbp2 = new MimeBodyPart();
//
//                              // attach the file to the message
//                              mbp2.attachFile(filename);
//
//                              /*
//                               * Use the following approach instead of the above line if
//                               * you want to control the MIME type of the attached file.
//                               * Normally you should never need to do this.
//                               *
//                               FileDataSource fds = new FileDataSource(filename) {
//                               public String getContentType() {
//                               return "application/octet-stream";
//                               }
//                               };
//                               mbp2.setDataHandler(new DataHandler(fds));
//                               mbp2.setFileName(fds.getName());
//                               */
//
//                              // create the Multipart and add its parts to it
//                              Multipart mp = new MimeMultipart();
//                              mp.addBodyPart(mbp1);
//                              mp.addBodyPart(mbp2);
//
//                              // add the Multipart to the message
//                              msg.setContent(mp);
//
//                              // set the Date: header
//                              msg.setSentDate(new Date());
//
//                              /*
//                               * If you want to control the Content-Transfer-Encoding
//                               * of the attached file, do the following.  Normally you
//                               * should never need to do this.
//                               *
//                               msg.saveChanges();
//                               mbp2.setHeader("Content-Transfer-Encoding", "base64");
//                               */
//
//                              // send the message
//                              Transport.send(msg);
//
//                    } catch (MessagingException mex) {
//                              mex.printStackTrace();
//                              Exception ex = null;
//                              if ((ex = mex.getNextException()) != null) {
//                                        ex.printStackTrace();
//                              }
//                    } catch (IOException ioex) {
//                              ioex.printStackTrace();
//                    }
//
//          }
}
