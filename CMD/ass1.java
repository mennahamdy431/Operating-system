import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
class Parser {

    String[] args;
    String cmd;

    public Boolean parse(String command) {
        String[] words = command.split(" ");

        ArrayList<String> parts;

        parts = new ArrayList<String>();

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
            if (words[i].length() > 0) {
                parts.add(words[i]);
            }
        }

        if (parts.size() == 0) {
            return false;
        }


        if (parts.get(0).equals("echo")) {
            parts.remove(0);
            cmd = "echo";
            if (parts.size() > 0) {
                args = new String[parts.size()];
                for (int j = 0; j < parts.size(); j++)
                    args[j] = parts.get(j);
                return true;
            } else
                return false;
        } else if (parts.get(0).equals("cd")) {
            cmd = parts.get(0);
            parts.remove(0);
            if (parts.size() == 1) {
                args = new String[1];
                args[0] = parts.get(0);
                return true;
            } else if (parts.size() > 1) {
                return false;
            } else {
                args = new String[1];
                args[0] = "";

                return true;
            }

        } else if (parts.get(0).equals("cp")) {
            //everything in the args;
            cmd = parts.get(0);
            parts.remove(0);
            if (parts.get(0).equals("-r")) {
                cmd += " -r";
                parts.remove(0);
            }
            if (parts.size() == 2) {
                args = new String[parts.size()];

                for (int j = 0; j < parts.size(); j++)
                    args[j] = parts.get(j);
                return true;
            } else
                return false;


        } else if (parts.get(0).equals("rm")) {

            if (parts.size() == 2) {
                args = new String[1];
                args[0] = parts.get(1);
                cmd = parts.get(0);
                return true;
            } else
                return false;

        } else if (parts.get(0).equals("mkdir")) {
            if (parts.size() < 2) {
                return false;
            } else {

                cmd = "mkdir";
                parts.remove(0);
                args = new String[parts.size()];
                for (int j = 0; j < parts.size(); j++)
                    args[j] = parts.get(j);
                return true;
            }
        } else if (parts.get(0).equals("rmdir")) {
            if (parts.size() != 2) {
                return false;
            }
            cmd = "rmdir";
            parts.remove(0);
            args = new String[parts.size()];
            for (int j = 0; j < parts.size(); j++)
                args[j] = parts.get(j);
            return true;
        } else if (parts.get(0).equals("pwd")) {
            if (parts.size() != 1) {
                return false;
            }
            cmd = "pwd";
            return true;
        } else if (parts.get(0).equals("ls")) {
            parts.remove(0);
            cmd = "ls";
            if (parts.size() > 0 && parts.get(0).equals("-r")) {
                cmd += " " + parts.get(0);
                parts.remove(0);
            }
            if (parts.size() > 0) {
                return false;
            }

            return true;

        } else if (parts.get(0).equals("cat")) {
            cmd = parts.get(0);
            parts.remove(0);
            if (parts.size() > 0 && parts.size() <= 2) {
                args = new String[parts.size()];

                for (int j = 0; j < parts.size(); j++)
                    args[j] = parts.get(j);

                return true;
            } else
                return false;
        } else if (parts.get(0).equals("touch")) {
            cmd = "touch";
            parts.remove(0);
            if (parts.size() != 1) {
                return false;
            }
            args = new String[1];
            args[0] = parts.get(0);
            return true;
        } else if (parts.get(0).equals("exit")) {
            System.exit(0);
        }
        return false;
    }

    public String getcmd() {
        return cmd;
    }

    public String[] getArgs() {
        return args;
    }
}


class Terminal {
    //Parser parser;
    private static String defaultDic = System.getProperty("user.dir");


    public void echo(String[] word) {
        for (String arg : word) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }

    public String pwd() {
        return defaultDic;
    }

    public void cd(String[] arg) {

        if (arg[0].equals("..")) {

            String[] path = defaultDic.split("\\\\");
            if (path.length > 1)
                defaultDic = "";

            for (int i = 0; i < path.length - 1; i++) {
                defaultDic += path[i] + "\\";
            }

        } else if (arg[0].equals("")) {
            String[] path = defaultDic.split("\\\\");

            defaultDic = path[0] + "\\";

        } else {


            File file = new File(arg[0]);

            if (!(file.isAbsolute())) {
                file = new File(defaultDic + "\\" + arg[0]);
                if (file.exists())
                    defaultDic = defaultDic + "\\" + arg[0];

            }
            if (arg[0].equals(file.getAbsolutePath()) && file.exists())
                defaultDic = arg[0];

        }

    }

    public void ls() throws IOException {
        File f1 = new File(defaultDic);
        File fileList[] = f1.listFiles();

        for (int i = 0; i < fileList.length; i++) {
            System.out.println(fileList[i].getName());
        }
    }

    public void ls_r() throws IOException {

        File f1 = new File(defaultDic);
        File fileList[] = f1.listFiles();

        for (int i = fileList.length - 1; i >= 0; i--) {
            System.out.println(fileList[i].getName());
        }
    }

    public void mkdir(String[] newDirs) throws IOException {
        for (int i = 0; i < newDirs.length; i++) {
            File directory = new File(newDirs[i]);

            if (!(directory.isAbsolute()))
                directory = new File(defaultDic + "\\" + newDirs[i]);

            if (!directory.exists())
                directory.mkdirs();
            else
                System.out.println("Faild The directory is exists!");

        }
    }

    public void rmdir(String filePath) {
        if (filePath.equals("*")) {

            File newdir = new File(defaultDic);

            File[] allfiles = newdir.listFiles();

            for (File f : allfiles) {
                if (f.isDirectory() && f.length() == 0)
                    f.delete();
            }

        } else {
            File file = new File(filePath);

            if (!file.isAbsolute())
                file = new File(defaultDic + "\\" + filePath);
            if (file.exists() && file.isDirectory()) {

                String[] files = file.list();

                if (file.length() > 0)
                    System.out.println("Failed Directory is not Empty!");
                else
                    file.delete();

            } else
                System.out.println("Directory Not Exist!");

        }
    }

    public void touch(String path) throws IOException {

        File f2 = new File(path);
        if (!f2.isAbsolute()) {
            f2 = new File(defaultDic + "\\" + path);
        }

        if (f2.exists()) {
            System.out.println("File exists, cannot create this folder");
        } else
            f2.createNewFile();

    }

    public void cp(String sourcePath, String destinationPath) throws IOException {

        File source = new File(sourcePath);
        File destination = new File(destinationPath);
        if (!source.isAbsolute())
            source = new File(defaultDic + "\\" + sourcePath);

        if (!destination.isAbsolute())
            destination = new File(defaultDic + "\\" + destinationPath);


        if (source.isFile() && !destination.exists())
            Files.copy(source.toPath(), destination.toPath());
        else
            System.out.println("Error: " + source.getName() + " Not Exist or " + destination.getName() + " Already exist!");
    }

    public void rm(String fileName) {

        File file = new File(defaultDic + "\\" + fileName);

        if (file.exists()){
            file.delete();
        }else
        {
            System.out.println("Error File Does not Exist!");
        }

    }

    public void cat(String[] filePath) {
        for (int i = 0; i < filePath.length; i++) {


            File file = new File(filePath[i]);
            if (!file.isAbsolute())
                file = new File(defaultDic + "\\" + filePath[i]);

            Scanner in = null;
            try {
                in = new Scanner(file);
                while (in.hasNextLine())
                    System.out.println(in.nextLine());
                in.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error " + e);
            }


        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        Parser p = new Parser();

        Terminal terminal = new Terminal();
        String CommandName, input;

        String[] Args;

        while (true) {
            System.out.print(defaultDic + " > ");

            input = in.nextLine();

            if (input.equals("")) continue;

            if (p.parse(input.trim())) {

                CommandName = p.getcmd();
                Args = p.getArgs();

                switch (CommandName) {

                    case "echo":
                        terminal.echo(Args);
                        break;

                    case "pwd":
                        System.out.println(terminal.pwd());
                        break;
                    case "cd":
                        terminal.cd(Args);
                        break;
                    case "ls":
                        terminal.ls();
                        break;
                    case "ls -r":
                        terminal.ls_r();
                        break;
                    case "mkdir":
                        terminal.mkdir(Args);
                        break;
                    case "rmdir":
                        terminal.rmdir(Args[0]);
                        break;
                    case "touch":
                        terminal.touch(Args[0]);
                        break;
                    case "cp":
                        terminal.cp(Args[0], Args[1]);
                        break;
                    case "cp -r":
                        //Add cp-r function call
                        //terminal.cp_r(Args[0],Args[1]);
                        break;
                    case "rm":
                        terminal.rm(Args[0]);
                        break;
                    case "cat":
                            terminal.cat(Args);
                            break;
                    default:
                        System.out.println("Invalid Command Name or parameter!");
                }


            } else
                System.out.println("Invalid Command Name or parameter!");
        }
    }
}