package test;

//import im.imo.services.base.Constants;
//import im.imo.services.base.ImoRuntimeException;
//import im.imo.services.imo_account_common.ImoAccountUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum ImExpirationSystemMessage {
    ADD_CONTACT("add_contact",
            "You've enabled Disappearing Message for new friends. All new messages in the chat will disappear automatically after %s. To change the setting, please update to the latest version.",
            "Your friend has enabled Disappearing Message for new friends. All new messages in the chat will disappear automatically after %s. To change the setting, please update to the latest version."),
    SET_IM_EXPIRATION("set_im_expiration",
            "I've enabled Disappearing Message. All new messages in the chat will disappear automatically after %s. To change the setting, please update to the latest version.",
            "Your friend has enabled Disappearing Message. All new messages in the chat will disappear automatically after %s. To change the setting, please update to the latest version."),
    DISABLE_IM_EXPIRATION("disable_im_expiration",
            "I've disabled Disappearing Message. To change the setting, please update to the latest version.",
            "Your friend has disabled Disappearing Message. To change the setting, please update to the latest version.");

    private static final long DAYS = 86400000;
    private String type;
    private String senderOldVersionMsg;
    private String recipientOldVersionMsg;

    ImExpirationSystemMessage(String type, String senderOldVersionMsg, String recipientOldVersionMsg) {
        this.type = type;
        this.senderOldVersionMsg = senderOldVersionMsg;
        this.recipientOldVersionMsg = recipientOldVersionMsg;
    }

    public String getType() {
        return type;
    }

//    private static final Logger logger = LogManager.getLogger();

    private static final Map<String, Map<String, Map<String, String>>> imExpirationMessages = null;

    private static Map<String, Map<String, Map<String, String>>> loadImExpirationMessages() {
        try {
            JSONParser parser = new JSONParser();
            InputStream is = ImExpirationSystemMessage.class.getResourceAsStream("/Users/bigo/IdeaProjects/test/src/test/im_expiration_system_message.json");

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject obj = (JSONObject) parser.parse(responseStrBuilder.toString());
            for (ImExpirationSystemMessage operation : EnumSet.allOf(ImExpirationSystemMessage.class)) {
                if (!obj.containsKey(operation.type)) {
                    // throw new Exception("missed operation: %s", operation);
                }
            }
            return obj;
        } catch (org.json.simple.parser.ParseException | IOException e) {
            // logger.error("ERROR:" + e.getMessage());
        }
        return null;
    }

//    public InputStream getResourceAsStream(String name) {
//        name = resolveName(name);
//        ClassLoader cl = getClassLoader0();
//        if (cl==null) {
//            // A system class.
//            return ClassLoader.getSystemResourceAsStream(name);
//        }
//        return cl.getResourceAsStream(name);
//    }

    public String getImExpirationSenderMessage(String language, Long imExpiration) {
        if (!imExpirationMessages.containsKey(this.type)) {
            return null;
        }
        return String.format(imExpirationMessages.get(this.type).get("sender").get(language), msToDay(imExpiration));
    }

    public String getImExpirationRecipientMessage(String language, Long imExpiration) {
        if (!imExpirationMessages.containsKey(this.type)) {
            return null;
        }
        return String.format(imExpirationMessages.get(this.type).get("recipient").get(language), msToDay(imExpiration));
    }

    private static String msToDay(Long imExpiration) {
        return (imExpiration / DAYS) + " days";
    }

    public static void main(String[] args) {
//        System.out.println(imExpirationMessages);
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = Arrays.asList(1, 2, 2, 4);
        list.stream().map(l -> {
            if (map.containsKey(l)) {
                ((List<Integer>)map.get(l)).add(l);
            } else {
                map.put(l, Arrays.asList(l));
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println(map);
    }
}
