package com.test.app.injection;

import com.google.inject.AbstractModule;
import com.test.app.processor.ChancesTextFileProcessor;
import com.test.app.processor.IChancesFileProcessor;
import com.test.app.processor.IScoreBoardProcessor;
import com.test.app.processor.IScoreProcessor;
import com.test.app.processor.ScoreProcessor;
import com.test.app.processor.StdOutScoreBoardProcessor;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IChancesFileProcessor.class).to(ChancesTextFileProcessor.class);
        bind(IScoreProcessor.class).to(ScoreProcessor.class);
        bind(IScoreBoardProcessor.class).to(StdOutScoreBoardProcessor.class);
    }

}
