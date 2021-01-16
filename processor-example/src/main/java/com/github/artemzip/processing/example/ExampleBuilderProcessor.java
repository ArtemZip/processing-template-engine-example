package com.github.artemzip.processing.example;

import com.github.artemzip.engine.TemplateContext;
import com.github.artemzip.engine.TemplateEngine;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = "com.github.artemzip.processing.example.ExampleBuilder")
public class ExampleBuilderProcessor extends AbstractProcessor {
    private static final TemplateEngine engine = TemplateEngine.getInstance()
               .registerTemplate("builder-template", "/processing-template-engine-example/processor-example/src/main/resources/BuilderTemplate.jpt");

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(ExampleBuilder.class).forEach(element -> {
            String qualifiedName = ((TypeElement) element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();
            String packageName = qualifiedName.replace("." + className, "");

            List<VariableElement> vars = ElementFilter.fieldsIn(((TypeElement) element).getEnclosedElements());

            List<TemplateContext> fieldsCtx = new LinkedList<>();
            vars.forEach(field -> {
                var ctx = new TemplateContext()
                        .addVar("TYPE", field.asType().toString())
                        .addVar("NAME", field.getSimpleName())
                        .addVar("CLASS_NAME", className);

                fieldsCtx.add(ctx);
            });

            var ctx = new TemplateContext()
                    .addVar("PACKAGE", packageName)
                    .addVar("IMPORTS", new TemplateContext().addVar("IMPORT", qualifiedName))
                    .addVar("CLASS_NAME", className)
                    .addVar("FIELDS", fieldsCtx);

            try {
                engine
                  .processTemplate("builder-template", ctx)
                  .write(processingEnv.getFiler(), qualifiedName + "Builder");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;

    }
}
