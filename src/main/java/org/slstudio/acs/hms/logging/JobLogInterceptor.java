package org.slstudio.acs.hms.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÏÂÎç4:06
 */
@Aspect
@Component
public class JobLogInterceptor {
    private static final Log log = LogFactory.getLog(JobLogInterceptor.class);

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRun(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job,context)",argNames = "jp, job, context")
    public void beforeBeginRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("]");
        log.info(msg.toString());
    }
    @AfterReturning(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRun(org.slstudio.acs.tr069.job.IDeviceJob,org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job, context)",argNames = "jp, job, context")
    public void afterReturningBeginRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context){
        if(job.isFinished()){
            StringBuilder msg = new StringBuilder();
            msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] has finished");
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRun(org.slstudio.acs.tr069.job.IDeviceJob,org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job, context)",argNames = "jp, job, context, exp", throwing = "exp")
    public void afterThrowingBeginRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] raise exception:").append(exp.getMessage());
        log.info(msg.toString());

    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob,org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void beforeBeginRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("]");
        log.info(msg.toString());
    }

    @AfterReturning(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob,org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void afterReturningBeginRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        if(job.isFinished()){
            StringBuilder msg = new StringBuilder();
            msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("] has finished");
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.beginRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob,org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message, exp", throwing = "exp")
    public void afterThrowingBeginRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message, Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after begin to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("] raise exception:").append(exp.getMessage());
        log.info(msg.toString());

    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRun(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job, context)",argNames = "jp, job, context")
    public void beforeContinueRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("]");
        log.info(msg.toString());
    }

    @AfterReturning(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRun(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job, context)",argNames = "jp, job, context")
    public void afterReturningContinueRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context){
        if(job.isFinished()){
            StringBuilder msg = new StringBuilder();
            msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] has finished");
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRun(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext)) && args(job, context)",argNames = "jp, job, context, exp", throwing = "exp")
    public void afterThrowingContinueRun(JoinPoint jp, IDeviceJob job, ITR069MessageContext context,Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] raise exception:").append(exp.getMessage());
        log.info(msg.toString());

    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void beforeContinueRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("]");
        log.info(msg.toString());
    }

    @AfterReturning(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void afterReturningContinueRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        if(job.isFinished()){
            StringBuilder msg = new StringBuilder();
            msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("] has finished");
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithRequest(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message, exp", throwing = "exp")
    public void afterThrowingContinueRunWithRequest(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message, Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with request[").append(message.getMessageID()).append("] raise exception:").append(exp.getMessage());
        log.info(msg.toString());

    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithResponse(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void beforeContinueRunWithResponse(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with response[").append(message.getMessageID()).append("]");
        log.info(msg.toString());
    }

    @AfterReturning(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithResponse(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message")
    public void afterReturningContinueRunWithResponse(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message){
        if(job.isFinished()){
            StringBuilder msg = new StringBuilder();
            msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with response[").append(message.getMessageID()).append("] has finished");
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.continueRunWithResponse(org.slstudio.acs.tr069.job.IDeviceJob, org.slstudio.acs.tr069.session.context.ITR069MessageContext, org.slstudio.acs.tr069.databinding.TR069Message)) && args(job, context, message)",argNames = "jp, job, context, message, exp", throwing = "exp")
    public void afterThrowingContinueRunWithResponse(JoinPoint jp, IDeviceJob job, ITR069MessageContext context, TR069Message message, Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] after continue to run in session[").append(context.getTR069SessionContext().getSessionID()).append("] with response[").append(message.getMessageID()).append("] raise exception:").append(exp.getMessage());
        log.info(msg.toString());

    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.failOnException(org.slstudio.acs.tr069.job.IDeviceJob, java.lang.Exception)) && args(job, exp)",argNames = "jp, job, exp")
    public void beforeFailOnException(JoinPoint jp, IDeviceJob job, Exception exp){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] failed on exception[").append(exp.getMessage()).append("]");
        log.info(msg.toString());
    }

    @Before(value = "execution(* org.slstudio.acs.tr069.job.runner.JobRunner.failOnTimeout(org.slstudio.acs.tr069.job.IDeviceJob, boolean)) && args(job, isWaitTimeout)",argNames = "jp, job, isWaitTimeout")
    public void beforeFailOnTimeout(JoinPoint jp, IDeviceJob job, boolean isWaitTimeout){
        StringBuilder msg = new StringBuilder();
        msg.append("job[").append(job.getJobID()).append("] for device[").append(job.getDeviceKey()).append("] failed on ").append(isWaitTimeout?"waiting timeout":"running timeout");
        log.info(msg.toString());
    }

}
