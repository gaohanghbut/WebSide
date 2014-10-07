package cn.hang.mvc.pipeline.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import cn.hang.mvc.common.util.InvocationUtils;
import cn.hang.mvc.common.util.TagNameUtils;
import cn.hang.mvc.exception.InitializeException;
import cn.hang.mvc.pipeline.ConditionPipeline;
import cn.hang.mvc.pipeline.ExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.Pipeline;
import cn.hang.mvc.pipeline.impl.DefaultConditionPipeline;
import cn.hang.mvc.pipeline.impl.DefaultExceptionHandlerPipeline;
import cn.hang.mvc.pipeline.impl.DefaultPipeline;
import cn.hang.mvc.pipeline.valve.ExceptionHandlerValve;
import cn.hang.mvc.pipeline.valve.IfElseIfValve;
import cn.hang.mvc.pipeline.valve.TryCatchFinallyValve;
import cn.hang.mvc.pipeline.valve.Valve;

/**
 * pipeline��ǩ�����������ڴ������ļ��й����ܵ�
 * 
 * @author GaoHang
 * 
 */
public class PipelineBeanDefinitionParser implements BeanDefinitionParser {

	/**
	 * Pipeline��Ӧ������
	 */
	private static final String CLASS = "class";

	/**
	 * �ܵ��������е�bean name
	 */
	private static final String PIPELINE_BEAN_NAME = "_pipeline_bean_";

	/**
	 * try��ǩ
	 */
	private static final String TRY = "try";

	/**
	 * catch��ǩ
	 */
	private static final String CATCH = "catch";

	/**
	 * finally��ǩ
	 */
	private static final String FINALLY = "finally";

	/**
	 * <if>��ǩ��
	 */
	private static final String IF = "if";

	/**
	 * <else-if>��ǩ��
	 */
	private static final String ELSE_IF = "else-if";

	/**
	 * <else>��ǩ
	 */
	private static final String ELSE = "else";
	
	/**
	 * �����ж�valve�к�׺����������
	 */
	private static final String URI_SUFIX = "uri-sufix";

	/**
	 * <if-else-if-valve>��ǩ
	 */
	private static final String IF_ELSE_IF_VALVE = "if-else-if-valve";

	/**
	 * <try-catch-finally-valve>��ǩ
	 */
	private static final String TRY_CATCH_FINALLY_VALVE = "try-catch-finally-valve";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		if (!parserContext.getRegistry().containsBeanDefinition(PIPELINE_BEAN_NAME)) {
			GenericBeanDefinition def = getDefaultBeanDefinition(element);
			MutablePropertyValues propertyValues = new MutablePropertyValues();
			propertyValues.addPropertyValue(extractValves(element, def));
			def.setPropertyValues(propertyValues);
			parserContext.registerBeanComponent(new BeanComponentDefinition(def, PIPELINE_BEAN_NAME));
		}
		return null;
	}

	/**
	 * ����Valve
	 * 
	 * @param element
	 */
	private PropertyValue extractValves(Element element, GenericBeanDefinition def) {
		List<Valve> valves = new ArrayList<Valve>();
		List<Element> elements = DomUtils.getChildElements(element);
		for (Element elem : elements) {
			valves.add(buildValve(elem));
		}
		PropertyValue propertyValue = new PropertyValue("valves", valves);
		return propertyValue;
	}

	private Valve buildTryCatchFinallyValve(Element element, Class<?> c) {
		try {
			TryCatchFinallyValve tryCatchFinallyValve = (TryCatchFinallyValve) c.newInstance();

			List<Element> subelems = DomUtils.getChildElements(element);
			String elemName;

			// �����ӱ�ǩ��Ӧ�Ĺܵ�
			for (Element elem : subelems) {
				elemName = TagNameUtils.getElementName(elem);
				if (TRY.equals(elemName)) {
					Pipeline pipeline = buildPipeline(elem);
					tryCatchFinallyValve.setTryPipeline(pipeline);
				} else if (CATCH.equals(elemName)) {
					ExceptionHandlerPipeline pipeline = buildExceptionHandlerPipeline(elem);
					tryCatchFinallyValve.setCatchPipeline(pipeline);
				} else if (FINALLY.equals(elemName)) {
					Pipeline pipeline = buildPipeline(elem);
					tryCatchFinallyValve.setFinallyPipeline(pipeline);
				}
			}
			return tryCatchFinallyValve;
		} catch (InstantiationException e) {
			throw new InitializeException(e);
		} catch (IllegalAccessException e) {
			throw new InitializeException(e);
		}
	}

	/**
	 * ����Valve�����ô���Valve����
	 * 
	 * @param elem
	 *            valve��Ӧ�ı�ǩ
	 * @return
	 */
	private Valve buildValve(Element elem) {
		String elemName;
		Class<?> valveClass;
		Valve valve = null;
		elemName = TagNameUtils.getElementName(elem);
		try {
			valveClass = Class.forName(elem.getAttribute(CLASS));
		} catch (ClassNotFoundException e) {
			throw new InitializeException(e);
		}
		if (!InvocationUtils.isImplementationOf(valveClass, Valve.class)) {
			throw new InitializeException("the class is not a valve:" + valveClass.getName());
		}

		if (elemName.equals(IF_ELSE_IF_VALVE)) {
			if (InvocationUtils.isImplementationOf(valveClass, IfElseIfValve.class)) {
				valve = buildIfElseIfValve(elem, valveClass);
			} else {
				throw new InitializeException("the class is not a if else if valve:" + elem.getAttribute(CLASS));
			}

		} else if (elemName.equals(TRY_CATCH_FINALLY_VALVE)) {
			if (InvocationUtils.isImplementationOf(valveClass, TryCatchFinallyValve.class)) {
				valve = buildTryCatchFinallyValve(elem, valveClass);
			} else {
				throw new InitializeException("the class is not a try catch finally valve:" + elem.getAttribute(CLASS));
			}
		} else {
			try {
				valve = (Valve) valveClass.newInstance();
			} catch (InstantiationException e) {
				throw new InitializeException(e);
			} catch (IllegalAccessException e) {
				throw new InitializeException(e);
			}
		}
		//����Valve�ĳ�ʼ������
		valve.init();
		return valve;
	}

	/**
	 * ����IfElseIfValve
	 * 
	 * @param element
	 *            valve��Ӧ�ı�ǩ
	 * @param c
	 *            valve������
	 * @return
	 */
	private IfElseIfValve buildIfElseIfValve(Element element, Class<?> c) {
		IfElseIfValve ifElseIfValve;
		try {
			ifElseIfValve = (IfElseIfValve) c.newInstance();
			List<Element> elems = DomUtils.getChildElements(element);
			String elemName;
			String urisufix;
			ConditionPipeline conditionPipeline;
			for (Element elem : elems) {
				elemName = TagNameUtils.getElementName(elem);
				urisufix = elem.getAttribute(URI_SUFIX);
				conditionPipeline = new DefaultConditionPipeline(buildPipeline(elem));
				conditionPipeline.setUrisufixs(Arrays.asList(urisufix.split(ConditionPipeline.SUFIX_DILIMETER)));
				if (IF.equals(elemName)) {
					ifElseIfValve.setIfPipeline(conditionPipeline);
				} else if (ELSE_IF.equals(elemName)) {
					ifElseIfValve.setElseIfPipeline(conditionPipeline);
				} else if (ELSE.equals(elemName)) {
					ifElseIfValve.setElsePipeline(conditionPipeline);
				}
			}
		} catch (InstantiationException e) {
			throw new InitializeException(e);
		} catch (IllegalAccessException e) {
			throw new InitializeException(e);
		}
		return ifElseIfValve;
	}

	/**
	 * ������ǰԪ�ر�ʾ���ӹܵ����ܵ��е�valve������elem���ӱ�ǩ��
	 * 
	 * @param elem
	 * @return
	 */
	private Pipeline buildPipeline(Element elem) {
		Pipeline pipeline = new DefaultPipeline();
		List<Valve> valves = getChildValveList(elem);
		pipeline.setValves(valves);
		pipeline.init();//���ó�ʼ��
		return pipeline;
	}
	
	/**
	 * �����쳣����ܵ�
	 * 
	 * @param elem
	 * @return
	 */
	private ExceptionHandlerPipeline buildExceptionHandlerPipeline(Element elem) {
		ExceptionHandlerPipeline pipeline = new DefaultExceptionHandlerPipeline();
		List<Valve> valves = getChildValveList(elem);
		for (Valve valve : valves) {
			if (!(valve instanceof ExceptionHandlerValve)) {
				throw new InitializeException("the valve is not an exception handler valve:" + valve.getClass().getName());
			}
		}
		pipeline.setValves(valves);
		pipeline.init();//���ó�ʼ��
		return pipeline;
	}

	/**
	 * ��ȡ��ǰ��ǩ���ӱ�ǩ���õ�Valve�б�
	 * 
	 * @param elem
	 *            ��ǰ��ǩ
	 * @return
	 */
	private List<Valve> getChildValveList(Element elem) {
		List<Element> elems = DomUtils.getChildElements(elem);
		List<Valve> valves = new ArrayList<Valve>(elems.size());
		for (Element valveElem : elems) {
			valves.add(buildValve(valveElem));
		}
		return valves;
	}

	/**
	 * ����Ĭ�����õ�bean��������������ڴ洢bean�Ļ�����Ϣ��������������Ϣ
	 * 
	 * @param element
	 * @return
	 */
	protected GenericBeanDefinition getDefaultBeanDefinition(Element element) {
		Class<?> c;
		try {
			c = Class.forName(element.getAttribute(CLASS));
		} catch (ClassNotFoundException e1) {
			throw new InitializeException(e1);
		}
		if (!InvocationUtils.isImplementationOf(c, Pipeline.class)) {
			throw new InitializeException("the class is not a pipeline:" + c.getName());
		}
		
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setAbstract(false);
		def.setAutowireCandidate(true);
		def.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		def.setBeanClassName(element.getAttribute(CLASS));
		def.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
		try {
			def.setBeanClass(Class.forName(def.getBeanClassName()));
		} catch (ClassNotFoundException e) {
			throw new InitializeException(e);
		}
		def.setInitMethodName("init");
		return def;
	}

}
