package main.service;

import main.api.response.TagListResponse;
import main.api.response.TagResponse;
import main.entities.Tags;
import main.respositories.PostRepository;
import main.respositories.TagRepository;

import java.util.List;
import java.util.Map;

public class TagService {
    private TagRepository tagRepository;
    private PostRepository postRepository;
    public TagListResponse getTags(String query) {
        int count = 0;
        for (int i=0;i<postRepository.count();i++) {
            for (int ii = 0;ii<postRepository.getOne(i).getTags().size();ii++) {
                if (postRepository.getOne(i).getTags().get(ii).equals(query)) {
                    count++;
                }
            }
        }
        double dWeight = count/postRepository.count();
        Map<String,Integer> list = null;
        for (int i=0;i<postRepository.count();i++) {
            for (int ii = 0;ii<postRepository.getOne(i).getTags().size();ii++) {
                list.put(postRepository.getOne(i).getTags().get(ii).getName(),list.get(list.containsKey(postRepository.getOne(i).getTags().get(ii).getName())).intValue()+1);
            }
        }
        String biggest = "Java";
        for (int i=0;i<list.size();i++) {
            Object[] obj = list.keySet().toArray();
            if (list.get(obj[i].toString()).intValue() > list.get(biggest).intValue() || list.get(biggest) == null) {
                biggest = obj[i].toString();
            }
        }
        double dWeightMax = list.get(biggest).intValue()/postRepository.count();
        double k = 1/dWeightMax;
        double weight = dWeight*k;
        List<TagResponse> tagResponses = null;
        TagListResponse tagListResponse = new TagListResponse();
        for (int i = 0;i < tagRepository.findAll().size();i++) {
            Tags tag = tagRepository.getOne(i);
            tagResponses.add(new TagResponse(tag.getName(),weight));
        }
        tagListResponse.setTagResponseBody(tagResponses);
        return tagListResponse;
    }
}
