package com.alelk.bcpt.importer.parser;

import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.result.OperationResult;
import io.reactivex.Flowable;

import java.io.File;


/**
 * Parser
 *
 * Created by Alex Elkin on 04.10.2017.
 */
public interface Parser {

    Flowable<OperationResult<BcptDtoBundle>> parse(File file);

}
