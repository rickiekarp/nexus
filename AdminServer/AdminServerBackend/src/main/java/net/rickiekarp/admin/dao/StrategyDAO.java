//package net.rickiekarp.admin.dao;
//
//import net.rickiekarp.admin.exception.DuplicateStrategyException;
//import net.rickiekarp.admin.exception.StrategyNotFoundException;
//import net.rickiekarp.admin.model.Strategy;
//
//import java.util.List;
//
//public interface StrategyDAO {
//
//    public void addStrategy(Strategy strategy) throws DuplicateStrategyException;
//
//    public Strategy getStrategy(int id) throws StrategyNotFoundException;
//
//    public void updateStrategy(Strategy strategy) throws StrategyNotFoundException, DuplicateStrategyException;
//
//    public void deleteStrategy(int id) throws StrategyNotFoundException;
//
//    public List<Strategy> getStrategies();
//
//}
