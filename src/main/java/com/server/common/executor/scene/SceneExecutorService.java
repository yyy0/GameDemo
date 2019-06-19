package com.server.common.executor.scene;

import com.server.common.command.AbstractSceneDelayCommand;
import com.server.common.command.AbstractSceneRateCommand;
import com.server.common.command.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuxianming
 * @date 2019/6/13 10:37
 */
@Component
public class SceneExecutorService {

    @Autowired
    public SceneExecutor sceneExecutor;

    public void submit(ICommand command) {
        if (command instanceof AbstractSceneRateCommand) {
            AbstractSceneRateCommand sceneRateCommand = (AbstractSceneRateCommand) command;
            sceneExecutor.schedule(sceneRateCommand, sceneRateCommand.getDelay(), sceneRateCommand.getPeriod());
        } else if (command instanceof AbstractSceneDelayCommand) {
            AbstractSceneDelayCommand sceneDelayCommand = (AbstractSceneDelayCommand) command;
            sceneExecutor.schedule(sceneDelayCommand, sceneDelayCommand.getDelay());
        } else {
            sceneExecutor.addTask(command);
        }
    }

    public void init() {
        sceneExecutor.start();
    }
}
