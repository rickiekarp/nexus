package com.rkarp.adminserver.service;

import java.util.List;

import com.rkarp.adminserver.exception.DuplicateStrategyException;
import com.rkarp.adminserver.exception.StrategyNotFoundException;
import com.rkarp.adminserver.model.Strategy;

public interface StrategyService {

    public void addStrategy(Strategy strategy) throws DuplicateStrategyException;

    public Strategy getStrategy(int id) throws StrategyNotFoundException;

    public void updateStrategy(Strategy strategy) throws StrategyNotFoundException, DuplicateStrategyException;

    public void deleteStrategy(int id) throws StrategyNotFoundException;

    public List<Strategy> getStrategies();

}
