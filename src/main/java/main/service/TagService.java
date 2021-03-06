package main.service;

import main.api.response.TagListResponse;
import main.api.response.TagResponse;
import main.entities.Posts;
import main.entities.Tags;
import main.respositories.PostRepository;
import main.respositories.TagRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TagService {
    private TagRepository tagRepository;
    private PostRepository postRepository;
    public TagListResponse getTags(String query) {
        int count = postRepository.countAllActiveAndAccepted().orElse(0);
        Map<String,Double> tagWeightsNoNormalize = null;
        for (int i=0;i<postRepository.count();i++) {
            Posts post = postRepository.getOne(i);
            for (int ii = 0;ii<post.getTags().size();ii++) {
                if (post.getIsActive() == 1) {
                    Tags tag = post.getTags().get(ii);
                    tagWeightsNoNormalize.put(tag.getName(),(double) tag.getPosts().size() / count);
                }
            }
        }
        double dWeight = count/postRepository.count();
        double k = 1 / tagWeightsNoNormalize.values()
                .stream().max(Comparator.naturalOrder()).orElse(0.0);
        Map<String, Double> tagWeightsNormalize = tagWeightsNoNormalize.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue() * k));
        double weight = dWeight*k;
        List<TagResponse> tagResponses = null;
        TagListResponse tagListResponse = new TagListResponse();
        tagWeightsNormalize.forEach((vv, v) -> tagResponses.add(new TagResponse(vv, v)));
        tagListResponse.setTagResponseBody(tagResponses);
        return tagListResponse;
    }
}
