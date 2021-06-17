package com.ustc.chain.core;

/**
 * 责任链
 * @author 叶嘉耘
 */
public class ResponsibleChain {
    /**
     * handler初始化器
     */
    private HandlerInitializer handlerInitializer;

    /**
     * 装配handler
     */
    public void loadHandler(HandlerInitializer handlerInitializer) {
        this.handlerInitializer = handlerInitializer;
        handlerInitializer.initChannel(handlerInitializer);
    }

    public ContextResponse execute() {
        // 执行责任链
        execHandler(handlerInitializer.getFirstHandler(), ContextHolder.getReq(),ContextHolder.getResp());

        // 获取执行结果
        ContextResponse resp = ContextHolder.getResp();

        // 情况ContextHolder中的request和response
        ContextHolder.clearReq();
        ContextHolder.clearResp();

        return resp;
    }

    private void execHandler(Handler handler, ContextRequest request, ContextResponse response) {
        // 执行当前结点处理
        handler.doHandler(request, response);
        // 如果存在下一个结点,则处理下一个结点
        if (handler.hasNext()) {
            execHandler(handler.getNext(), ContextHolder.getReq(), ContextHolder.getResp());
        }
    }
}