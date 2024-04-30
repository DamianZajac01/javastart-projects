package com.example.readstack.domain.api;

import com.example.readstack.domain.discovery.Discovery;
import com.example.readstack.domain.discovery.DiscoveryDao;
import com.example.readstack.domain.user.UserDao;
import com.example.readstack.domain.vote.VoteDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DiscoveryService {
    private final DiscoveryDao discoveryDao = new DiscoveryDao();
    private final DiscoveryMapper discoveryMapper = new DiscoveryMapper();

    public void add(DiscoverySaveRequest saveRequest) {
        Discovery discoveryToSave = discoveryMapper.map(saveRequest);
        discoveryDao.save(discoveryToSave);
    }

    public List<DiscoveryBasicInfo> findAll() {
        return discoveryDao.findAll()
                .stream()
                .map(DiscoveryMapper::map)
                .collect(Collectors.toList());
    }

    public List<DiscoveryBasicInfo> findAllByCategory(int categoryId) {
        return discoveryDao.findByCategory(categoryId)
                .stream()
                .map(DiscoveryMapper::map)
                .collect(Collectors.toList());
    }

    private static class DiscoveryMapper {
        private static final UserDao userDao = new UserDao();
        private static final VoteDao voteDao = new VoteDao();

        static DiscoveryBasicInfo map(Discovery discovery) {
            return new DiscoveryBasicInfo(
                    discovery.getId(),
                    discovery.getTitle(),
                    discovery.getUrl(),
                    discovery.getDescription(),
                    discovery.getDateAdded(),
                    voteDao.countByDiscoveryId(discovery.getId()),
                    userDao.findById(discovery.getUserId()).orElseThrow().getUsername()
            );
        }

        static Discovery map(DiscoverySaveRequest discoverySaveRequest) {
            return new Discovery(
                    discoverySaveRequest.getTitle(),
                    discoverySaveRequest.getUrl(),
                    discoverySaveRequest.getDescription(),
                    LocalDateTime.now(),
                    discoverySaveRequest.getCategoryId(),
                    userDao.findByUsername(discoverySaveRequest.getAuthor())
                            .orElseThrow()
                            .getId()
            );
        }
    }
}
