package cz.vse.mapping;

import cz.vse.dto.*;
import cz.vse.entity.*;
import cz.vse.mapping.utils.CustomMapperMap;
import cz.vse.repository.TSMusterRepository;
import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by pcejka on 10.10.2016.
 */
@Component
public class MappingConfigurator extends ConfigurableMapper implements ApplicationContextAware {
    public MappingConfigurator() {
        super(false);
    }

    private MapperFactory factory;

    private ApplicationContext applicationContext;

    private CustomMapperMap customMappers;

    @Override
    protected void configure(final MapperFactory factory) {
        this.factory = factory;
        configureConverters(applicationContext);
        configureCustomMappers();
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.init();
        configureClassMaps();
    }

    private void configureConverters(final ApplicationContext applicationContext) {
        final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
        for (final Converter converter : converters.values()) {
            addConverter(converter);
        }
    }

    public void addConverter(final Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    private void configureCustomMappers() {
        final Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
        customMappers = new CustomMapperMap(mappers.values());
    }

    @Autowired
    TSMusterRepository tsMusterRepository;

    private void configureClassMaps() {

        factory.classMap(Project.class, ProjectDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("projectOwner", "projectOwner_id")
//                .field("personMembers{id}", "projectMembers_id{}")
                .field("personMembers", "projectMembers_id")
                .field("testSuites", "suites_id")
                .field("tcMusters", "tcMusters_id")
                .byDefault()
                .register();
        factory.classMap(DefectCommentDTO.class, DefectComment.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("defect_id", "defect.id")
                .field("author_id", "author.id")
                .byDefault()
                .register();
        factory.classMap(Defect.class, DefectDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .byDefault()
                .register();
        factory.classMap(Person.class, PersonDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .customize((Mapper<Person, PersonDTO>) customMappers.get(Person.class, PersonDTO.class))
//                .field("userRole", "userRolesEnum")
                .byDefault()
                .register();
        factory.classMap(TCMuster.class, TCMusterDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("project", "project_id")
                .field("tsMusters", "tsMusters_id")
                .field("tcInstances", "tcInstances_id")
                .field("testSuites", "testSuite_id")
                .customize((Mapper<TCMuster, TCMusterDTO>) customMappers.get(TCMuster.class, TCMusterDTO.class))
                .byDefault()
                .register();
        factory.classMap(TSMuster.class, TSMusterDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("author", "author_id")
                .field("tCMuster", "tcMuster_id")
                .byDefault()
                .register();
        factory.classMap(TestSuite.class, TestSuiteDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("tcMusters", "tcMusters_id")
                .field("project", "project_id")
                .field("tcMusters", "tcMusters_id")
//                .field("tcMusters{id}", "tcMusters_id")
                .byDefault()
                .register();
        factory.classMap(TSInstance.class, TSMuster.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .customize((Mapper<TSInstance, TSMuster>) customMappers.get(TSInstance.class, TSMuster.class))
                .byDefault()
                .register();
        factory.classMap(TCInstance.class, TCInstanceRunDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("id", "id")
                .field("name", "name")
                .field("tCMuster", "tcMusters_id")
                .field("tsInstances", "tsInstances_id")
                .field("id", "tcInstance_id")
                .field("tester", "tester_id")
                .register();
        factory.classMap(TSInstance.class, TSInstanceRunDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .field("tCInstance", "tcInstance_id")
                .field("tester", "tester_id")
                .byDefault()
                .register();
        factory.classMap(TCInstance.class, TCInstanceDTO.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
//                .field("tCInstance", "tcInstance_id")
                .field("tester", "tester_id")
                .field("tCMuster", "tcMuster_id")
                .customize((Mapper<TCInstance, TCInstanceDTO>) customMappers.get(TCInstance.class, TCInstanceDTO.class))
                .byDefault()
                .register();
        factory.classMap(Project.class, ProjectsNamesDTO.class)
                .field("projectOwner.name", "projectOwnerName")
//                .field("testSuites{id}", "suiteIdList{}")
                .field("testSuites", "suiteIdList")
                .byDefault()
                .register();
    }
}