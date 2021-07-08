package com.azure.tools.bomgenerator;

import java.util.regex.Matcher;

import static com.azure.tools.bomgenerator.Utils.AZURE_CORE_GROUPID;
import static com.azure.tools.bomgenerator.Utils.COMMANDLINE_EXTERNALDEPENDENCIES;
import static com.azure.tools.bomgenerator.Utils.COMMANDLINE_GROUPID;
import static com.azure.tools.bomgenerator.Utils.COMMANDLINE_INPUTFILE;
import static com.azure.tools.bomgenerator.Utils.COMMANDLINE_OUTPUTFILE;
import static com.azure.tools.bomgenerator.Utils.COMMANDLINE_POMFILE;

public class Main {

    public static void main(String[] args) {
        BomGenerator generator = new BomGenerator();
        parseCommandLine(args, generator);
        generator.generate();
    }

    private static void parseCommandLine(String[] args, BomGenerator generator) {
        for (String arg : args) {
            Matcher matcher = Utils.COMMANDLINE_REGEX.matcher(arg);
            if (matcher.matches()) {
                if (matcher.groupCount() == 2) {
                    String argName = matcher.group(1);
                    String argValue = matcher.group(2);

                    switch (argName.toLowerCase()) {
                        case COMMANDLINE_INPUTFILE:
                            generator.setInputFile(argValue);
                            break;

                        case COMMANDLINE_OUTPUTFILE:
                            generator.setOutputFile(argValue);
                            break;

                        case COMMANDLINE_POMFILE:
                            generator.setPomFile(argValue);
                            break;

                        case COMMANDLINE_EXTERNALDEPENDENCIES:
                            generator.setExternalDependenciesFile((argValue));
                            break;


//                        case COMMANDLINE_GROUPID:
//                            switch(argValue) {
//                                case AZURE_CORE_GROUPID:
//                                    generator.setGroupId(AZURE_CORE_GROUPID);
//                                break;
//
//                                default:
//                                    throw new UnsupportedOperationException();
//                            }
                    }
                }
            }
        }
    }
}
