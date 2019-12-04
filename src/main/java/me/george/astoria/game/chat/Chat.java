package me.george.astoria.game.chat;

import me.george.astoria.game.Rank;
import me.george.astoria.game.player.APlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static me.george.astoria.game.player.APlayer.getInstanceOfPlayer;

public class Chat implements Listener {

    public static volatile boolean chatEnabled = true;

    public final static int MAXIMUM_CHAT_CHARACTERS = 70;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player pl = event.getPlayer();
        APlayer player = getInstanceOfPlayer(pl);

        Rank rank = player.getRank();

        event.setCancelled(true);

        String message = event.getMessage();

        if (containsIllegal(message)) {
            player.sendMessage(ChatColor.RED + "Your message contained invalid characters.");
            return;
        }

        if (!(player.isStaff()) && message.length() > MAXIMUM_CHAT_CHARACTERS - 1) {
            player.sendMessage(ChatColor.RED + "Your message was too long. Maximum Characters: " + MAXIMUM_CHAT_CHARACTERS);
            return;
        }

        String checkedMessage = player.isStaff() ? message : checkForBannedWords(message);
        String formattedMessage = rank.getChatPrefix() + player.getDisplayName() + ": " + ChatColor.WHITE + checkedMessage;

        if (!chatEnabled && !player.isStaff()) {
            player.sendMessage(ChatColor.RED + "The chat is muted.");
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(formattedMessage));

        Bukkit.getLogger().info("[CHAT] " + formattedMessage);
    }

    public List<String> bannedWords = new ArrayList<>(Arrays.asList(
            "shit", "fuck", "cunt", "bitch", "whore",
            "slut", "wank", "asshole", "cock", "dick",
            "clit", "homo", "fag", "faggot", "queer",
            "nigger", "n1gger", "n1gg3r", "nigga", "dike",
            "dyke", "retard", " " + "motherfucker",
            "pussy", "rape", "cunt", "faggot", "blowjob",
            "handjob", "bast", "minecade", "d1ck", "wynncraft", "hitler",
            "fucked", "niger", "kys"));

    private String toCensor(int characters) {
        String result = "";
        for (int i = 0; i < characters; i++)
            result = result.concat("*");
        return result;
    }


    private String checkForBannedWords(String msg) {
        String result = msg;

        result = result.replace("ð", "");

        for (String word : bannedWords) result = replaceOperation(result, word);

        StringTokenizer st = new StringTokenizer(result);

        String string = "";
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            for (String word : bannedWords)
                if (token.contains(word)) {
                    List<Integer> positions = new ArrayList<>();
                    for (int i = 0; i < token.length(); i++)
                        if (Character.isUpperCase(token.charAt(i))) positions.add(i);

                    if (token.toLowerCase().contains(word.toLowerCase())) {
                        token = token.toLowerCase().replaceAll(word.toLowerCase(), " " + toCensor(word.length()));
                    }

                    for (int i : positions)
                        if (i < token.length()) Character.toUpperCase(token.charAt(i));
                }
            string += token + " ";
        }
        return string.trim();
    }

    private boolean containsBannedWords(String msg) {
        String result = msg;

        result = result.replace("ð", "");

        StringTokenizer st = new StringTokenizer(result);

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            for (String word : bannedWords)
                if (token.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
        }
        return false;
    }

    private String replaceOperation(String source, String search) {
        int length = search.length();
        if (length < 2) return source;

        // - Ignore the same character mutliple times in a row
        // - Ignore any non-alphabetic characters
        // - Ignore any digits and whitespaces between characters
        StringBuilder sb = new StringBuilder(4 * length - 3);
        for (int i = 0; i < length - 1; i++) {
            sb.append("([\\W\\d]*").append(Pattern.quote("" + search.charAt(i))).append(")+");
        }

        sb.append("([\\W\\d\\s]*)+");
        sb.append(search.charAt(length - 1));

        String temp = source.replaceAll("(?i)" + sb.toString(), search).trim();

        int wordCount = temp.split("\\s").length;

        String replace = source;

        if (wordCount <= 2)
            replace = " " + source;
        return replace.replaceAll("(?i)" + sb.toString(), " " + search).trim();
    }

    public boolean containsIllegal(String s) {
        //return s.matches("\\p{L}+") || s.matches("\\w+");
        //Probably have an array of allowed characters aswell.
        return !s.replace(" ", "").matches("[\\w\\Q!\"#$%&'()*çáéíóúâêôãõàüñ¿¡+,-./:;<=>?@[\\]^_`{|}~\\E]+");
    }
}
