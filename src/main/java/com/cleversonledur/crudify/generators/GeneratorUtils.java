package com.cleversonledur.crudify.generators;

import java.util.ArrayList;
import java.util.List;

import com.cleversonledur.crudify.generators.impl.ControllerGenerator;
import com.cleversonledur.crudify.generators.impl.DtoGenerator;
import com.cleversonledur.crudify.generators.impl.RepositoryGenerator;
import com.cleversonledur.crudify.generators.impl.ServiceGenerator;

public class GeneratorUtils {

    public static List<Generator> getGenerators() {
        List<Generator> generators = new ArrayList<>();
        generators.add(new DtoGenerator());
        generators.add(new RepositoryGenerator());
        generators.add(new ServiceGenerator());
        generators.add(new ControllerGenerator());
        return generators;
    }
}
