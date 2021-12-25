package com.zyc;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author zhuyc
 * @date 2021/12/25 10:46
 */
public class HolidayRequest {

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        /*
         * 要将流程定义部署到 Flowable 引擎，需要使用RepositoryService，它可以从ProcessEngine对象中检索。
         * 使用RepositoryService，通过传递 XML 文件的位置并调用deploy()方法来实际执行它来创建一个新的Deployment。
         */
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        /*
         * 我们现在可以通过 API 查询流程定义来验证引擎是否知道流程定义（并了解有关 API 的一些知识）。
         * 这是通过RepositoryService创建一个新的ProcessDefinitionQuery对象来完成的。
         */
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());

    }


    /**
     * 启动流程实例
     * 我们现在已将流程定义部署到流程引擎，因此可以使用此流程定义作为“蓝图”来启动流程实例
     * 要启动流程实例，我们需要提供一些初始流程变量。通常，当流程由自动触发时，您将通过呈现给用户的表单或通过 REST API 获取这些信息。
     * 在这个例子中，我们将保持简单并使用 java.util.Scanner 类在命令行上简单地输入一些数据：
     */
    public void startProcessInstance(ProcessEngine processEngine) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        /*
         * 通过RuntimeService启动一个流程实例。
         * 收集的数据作为java.util.Map实例传递，其中键是稍后将用于检索变量的标识符。流程实例是使用key启动的。
         * 该key与在 BPMN 2.0 XML 文件中设置的id属性相匹配，在本例中为HolidayRequest。
         * （注意：除了使用密钥之外，您稍后将学习许多方法来启动流程实例）
         */
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);

        /*
         * 当流程实例启动时，将创建一个 execution 并将其放入开始事件中。
         * 从那里，此execution 跟随到 经理批准的用户任务 的顺序流并执行用户任务行为。此行为将在数据库中创建一个任务，稍后可以使用查询找到该任务。
         * 用户任务处于等待状态，引擎将停止进一步执行任何操作，返回 API 调用。
         */
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);



    }



}
